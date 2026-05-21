package com.babacarmane.studentmanagerbackend.mapper;

import com.babacarmane.studentmanagerbackend.dto.EtudiantRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.EtudiantResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Etudiant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// mapper/EtudiantMapper.java
@Component
public class EtudiantMapper {

    // Entité → ResponseDTO
    public EtudiantResponseDTO toResponseDTO(Etudiant etudiant) {
        return EtudiantResponseDTO.builder()
                .id(etudiant.getId())
                .nom(etudiant.getNom())
                .prenom(etudiant.getPrenom())
                .email(etudiant.getEmail())
                .matricule(etudiant.getMatricule())
                // ↑ on ne met PAS les notes
                //   le client les demande via /api/notes/etudiant/{id}
                .build();
    }

    // RequestDTO → Entité
    public Etudiant toEntity(EtudiantRequestDTO dto) {
        return Etudiant.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .matricule(dto.getMatricule())
                // ↑ pas d'id — généré par la base
                // ↑ pas de notes — liste vide par défaut
                .build();
    }

    // Liste
    public List<EtudiantResponseDTO> toResponseDTOList(List<Etudiant> etudiants) {
        return etudiants.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
