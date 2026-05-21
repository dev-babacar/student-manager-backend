package com.babacarmane.studentmanagerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursResponseDTO {

    private Long id;
    private String nom;
    private String code;
    private Integer coefficient;
    // ↑ pas de List<Note> — évite la boucle infinie
}
