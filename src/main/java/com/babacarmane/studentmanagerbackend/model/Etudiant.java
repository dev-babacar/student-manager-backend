package com.babacarmane.studentmanagerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Data
@Table(name = "etudiants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false)
    private String prenom;

    @Email(message = "Email invalide")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String matricule;

    /**
     * Notes associées à l'étudiant.
     * Cascade.ALL pour que les opérations sur l'étudiant se répercutent sur ses notes.
     * FetchType.LAZY pour éviter le chargement inutile.
     */
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL ,  orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Note> notes;
}
