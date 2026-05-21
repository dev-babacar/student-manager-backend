package com.babacarmane.studentmanagerbackend.mapper;

import com.babacarmane.studentmanagerbackend.dto.NoteResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// mapper/NoteMapper.java
@Component
public class NoteMapper {

    // ─────────────────────────────────────
    // Note (entité) → NoteResponseDTO
    // Sens : base de données → client
    // ─────────────────────────────────────
    public NoteResponseDTO toResponseDTO(Note note) {

        return NoteResponseDTO.builder()
                .id(note.getId())
                .valeur(note.getValeur())
                .etudiantId(note.getEtudiant().getId())
                .etudiantNom(note.getEtudiant().getNom())
                .etudiantPrenom(note.getEtudiant().getPrenom())
                .coursId(note.getCours().getId())
                .coursNom(note.getCours().getNom())
                .coursCoefficient(note.getCours().getCoefficient())
                .build();
    }


    // ─────────────────────────────────────
    // NoteRequestDTO → Note (entité)
    // Sens : client → base de données
    // ─────────────────────────────────────
    // Attention : ce mapper ne peut PAS faire ça seul
    // car il a besoin des objets Etudiant et Cours complets
    // (pas juste leurs ids)
    // C'est pourquoi on le fait dans le Service
    // Le Mapper gère les conversions SIMPLES
    // ─────────────────────────────────────
    // Liste d'entités → liste de DTOs
    public List<NoteResponseDTO> toResponseDTOList(List<Note> notes) {
        return notes.stream()
                .map(this::toResponseDTO)
                //   ↑ pour chaque Note dans la liste
                //     appelle toResponseDTO(note)
                //     et collecte les résultats
                .collect(Collectors.toList());
    }
}
