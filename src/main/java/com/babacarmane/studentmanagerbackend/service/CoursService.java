package com.babacarmane.studentmanagerbackend.service;

import com.babacarmane.studentmanagerbackend.dto.CoursRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.CoursResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Cours;
import com.babacarmane.studentmanagerbackend.exception.CoursNotFoundException;
import com.babacarmane.studentmanagerbackend.mapper.CoursMapper;
import com.babacarmane.studentmanagerbackend.repository.CoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CoursRepository coursRepository;
    private final CoursMapper coursMapper;
    // ↑ NOUVEAU


    public Page<CoursResponseDTO> getAllCours(Pageable pageable) {
        return coursRepository.findAll(pageable)
                .map(coursMapper::toResponseDTO);
    }


    public CoursResponseDTO getCoursById(Long id) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new CoursNotFoundException(id));
        return coursMapper.toResponseDTO(cours);
    }


    public CoursResponseDTO createCours(CoursRequestDTO dto) {
        // ↑ AVANT : recevait Cours directement
        //   APRÈS : reçoit CoursRequestDTO

        Cours cours = coursMapper.toEntity(dto);
        // ↑ NOUVEAU — Mapper convertit RequestDTO → Entité

        Cours saved = coursRepository.save(cours);
        return coursMapper.toResponseDTO(saved);
    }


    public CoursResponseDTO updateCours(Long id, CoursRequestDTO dto) {
        Cours existant = coursRepository.findById(id)
                .orElseThrow(() -> new CoursNotFoundException(id));

        existant.setNom(dto.getNom());
        existant.setCode(dto.getCode());
        existant.setCoefficient(dto.getCoefficient());
        // ↑ AVANT : il y avait existant.setNom() en double — corrigé

        Cours saved = coursRepository.save(existant);
        return coursMapper.toResponseDTO(saved);
    }


    public void deleteCours(Long id) {
        Cours existant = coursRepository.findById(id)
                .orElseThrow(() -> new CoursNotFoundException(id));
        coursRepository.delete(existant);
    }
}