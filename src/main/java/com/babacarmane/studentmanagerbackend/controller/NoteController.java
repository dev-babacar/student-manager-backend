package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.NoteRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.NoteResponseDTO;
import com.babacarmane.studentmanagerbackend.exception.ErrorResponse;
import com.babacarmane.studentmanagerbackend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Gestion des notes")
public class NoteController {

    private final NoteService noteService;


    @GetMapping
    @Operation(
            summary = "Lister tous les notes",
            description = "Retourne la liste complète de tous les notes"
    )
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    public ResponseEntity<Page<NoteResponseDTO>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id")
            Pageable pageable) {
        return ResponseEntity.ok(noteService.getAllNotes(pageable));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une note par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note trouvé"),
            @ApiResponse(responseCode = "404", description = "Note introuvable",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<NoteResponseDTO> getById(@PathVariable Long id) {
        // ↑ AVANT : ResponseEntity<Note>
        // ↑ APRÈS : ResponseEntity<NoteResponseDTO>
        return ResponseEntity.ok(noteService.getNoteById(id));
    }


    @GetMapping("/etudiant/{etudiantId}")
    @Operation(summary = "Récupérer le note d'un étudiant par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note Etudiant trouvé"),
            @ApiResponse(responseCode = "404", description = "Note Etudiant introuvable",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<NoteResponseDTO>> getByEtudiant(
            @PathVariable Long etudiantId) {
        // ↑ AVANT : ResponseEntity<List<Note>>
        // ↑ APRÈS : ResponseEntity<List<NoteResponseDTO>>
        return ResponseEntity.ok(noteService.getNotesByEtudiant(etudiantId));
    }


    @GetMapping("/cours/{coursId}")
    @Operation(summary = "Récupérer les notes par cours")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notes Cours trouvé"),
            @ApiResponse(responseCode = "404", description = "Notes Cours introuvable",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<NoteResponseDTO>> getByCours(
            @PathVariable Long coursId) {
        return ResponseEntity.ok(noteService.getNotesByCours(coursId));
    }


    @PostMapping
    @Operation(summary = "Créer une nouvelle note")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Note créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<NoteResponseDTO> create(
            @Valid @RequestBody NoteRequestDTO dto) {
        NoteResponseDTO nouvelle = noteService.createNote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelle);
        // ↑ AVANT : body(nouvelle) retournait une Note
        // ↑ APRÈS : retourne NoteResponseDTO
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifier une note existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note modifiée"),
            @ApiResponse(responseCode = "404", description = "Note introuvable")
    })
    public ResponseEntity<NoteResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDTO dto) {
        return ResponseEntity.ok(noteService.updateNote(id, dto));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une note")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Note supprimée"),
            @ApiResponse(responseCode = "404", description = "Note introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }


    // ─────────────────────────────────────
    // NOUVEAU — endpoints pour les moyennes
    // ─────────────────────────────────────

    @GetMapping("/moyenne/etudiant/{etudiantId}")
    @Operation(
            summary = "moyenne étudiant",
            description = "Retourne la moyenne d'un étudiant"
    )
    @ApiResponse(responseCode = "200", description = "Moyenne retournée avec succès")
    public ResponseEntity<Double> getMoyenneByEtudiant(
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(noteService.getMoyenneByEtudiant(etudiantId));
    }

    @GetMapping("/moyenne/cours/{coursId}")
    @Operation(
            summary = "moyenne cours",
            description = "Retourne la moyenne d'un cours"
    )
    @ApiResponse(responseCode = "200", description = "Moyenne Cours retournée avec succès")
    public ResponseEntity<Double> getMoyenneByCours(
            @PathVariable Long coursId) {
        return ResponseEntity.ok(noteService.getMoyenneByCours(coursId));
    }
}