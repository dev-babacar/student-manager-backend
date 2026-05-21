package com.babacarmane.studentmanagerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto/EtudiantResponseDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantResponseDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String matricule;
    // ↑ pas de List<Note> — évite la boucle infinie
    //   si le client veut les notes, il appelle /api/notes/etudiant/{id}
}
