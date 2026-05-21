package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.NoteRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.NoteResponseDTO;
import com.babacarmane.studentmanagerbackend.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
//               ↑ AVANT : "/api/note" — corrigé en "/api/notes" (pluriel)
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;


    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAll() {
        // ↑ AVANT : ResponseEntity<List<Note>>
        // ↑ APRÈS : ResponseEntity<List<NoteResponseDTO>>
        return ResponseEntity.ok(noteService.getAllNotes());
    }


    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getById(@PathVariable Long id) {
        // ↑ AVANT : ResponseEntity<Note>
        // ↑ APRÈS : ResponseEntity<NoteResponseDTO>
        return ResponseEntity.ok(noteService.getNoteById(id));
    }


    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<NoteResponseDTO>> getByEtudiant(
            @PathVariable Long etudiantId) {
        // ↑ AVANT : ResponseEntity<List<Note>>
        // ↑ APRÈS : ResponseEntity<List<NoteResponseDTO>>
        return ResponseEntity.ok(noteService.getNotesByEtudiant(etudiantId));
    }


    @GetMapping("/cours/{coursId}")
    public ResponseEntity<List<NoteResponseDTO>> getByCours(
            @PathVariable Long coursId) {
        return ResponseEntity.ok(noteService.getNotesByCours(coursId));
    }


    @PostMapping
    public ResponseEntity<NoteResponseDTO> create(
            @Valid @RequestBody NoteRequestDTO dto) {
        NoteResponseDTO nouvelle = noteService.createNote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelle);
        // ↑ AVANT : body(nouvelle) retournait une Note
        // ↑ APRÈS : retourne NoteResponseDTO
    }


    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDTO dto) {
        return ResponseEntity.ok(noteService.updateNote(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }


    // ─────────────────────────────────────
    // NOUVEAU — endpoints pour les moyennes
    // ─────────────────────────────────────

    @GetMapping("/moyenne/etudiant/{etudiantId}")
    public ResponseEntity<Double> getMoyenneByEtudiant(
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(noteService.getMoyenneByEtudiant(etudiantId));
    }

    @GetMapping("/moyenne/cours/{coursId}")
    public ResponseEntity<Double> getMoyenneByCours(
            @PathVariable Long coursId) {
        return ResponseEntity.ok(noteService.getMoyenneByCours(coursId));
    }
}