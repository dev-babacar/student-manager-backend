package com.babacarmane.studentmanagerbackend.mapper;

import com.babacarmane.studentmanagerbackend.dto.CoursRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.CoursResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Cours;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoursMapper {

    public CoursResponseDTO toResponseDTO(Cours cours) {
        return CoursResponseDTO.builder()
                .id(cours.getId())
                .nom(cours.getNom())
                .code(cours.getCode())
                .coefficient(cours.getCoefficient())
                .build();
    }

    public Cours toEntity(CoursRequestDTO dto) {
        return Cours.builder()
                .nom(dto.getNom())
                .code(dto.getCode())
                .coefficient(dto.getCoefficient())
                .build();
    }

    // ← MANQUAIT — ajoute cette méthode
    public List<CoursResponseDTO> toResponseDTOList(List<Cours> cours) {
        return cours.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}