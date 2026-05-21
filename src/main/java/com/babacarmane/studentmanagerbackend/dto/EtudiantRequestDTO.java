package com.babacarmane.studentmanagerbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Ce que le client envoie pour créer/modifier un étudiant
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    private String matricule;
    // ↑ pas d'id — le client ne choisit pas son id
    // ↑ pas de notes — on ne crée pas des notes via l'étudiant
}
