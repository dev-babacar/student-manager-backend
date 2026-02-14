package com.babacarmane.studentmanagerbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "notes" ,
        uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id", "cours_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valeur de la note (par exemple entre 0 et 20)
     */
    @Min(value = 0, message = "La note doit être >= 0")
    @Max(value = 20, message = "La note doit être <= 20")
    private double valeur;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    @ToString.Exclude
    private Etudiant etudiant;

    /**
     * Lien vers le cours — Many notes pour un cours
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id", nullable = false)
    @ToString.Exclude
    private Cours cours;
}
