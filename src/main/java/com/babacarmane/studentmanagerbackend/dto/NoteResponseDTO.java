package com.babacarmane.studentmanagerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDTO {

    private Long id;
    private double valeur;

    private Long etudiantId;
    private String etudiantNom;
    private String etudiantPrenom;

    private Long coursId;
    private String coursNom;
    private Integer coursCoefficient;
}
