package com.babacarmane.studentmanagerbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cours")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du cours est obligatoire")
    @Column(nullable = false)
    private String nom;

    @Column(unique = true)
    private String code;

    /**
     * Coefficient du cours (ex: 1, 2, 3...)
     */
    private Integer coefficient = 1;

    /**
     * Liste des notes liées à ce cours
     */
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Note> notes = new ArrayList<>();
}