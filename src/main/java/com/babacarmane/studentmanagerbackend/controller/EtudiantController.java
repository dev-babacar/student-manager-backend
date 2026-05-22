package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.EtudiantRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.EtudiantResponseDTO;
import com.babacarmane.studentmanagerbackend.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.babacarmane.studentmanagerbackend.exception.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
@Tag(name = "Etudiants", description = "Gestion des étudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;


    @GetMapping
    @Operation(
            summary = "Lister tous les étudiants",
            description = "Retourne la liste complète de tous les étudiants"
    )
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    public ResponseEntity<List<EtudiantResponseDTO>> getAll() {
        // ↑ AVANT : ResponseEntity<List<Etudiant>>
        // ↑ APRÈS : ResponseEntity<List<EtudiantResponseDTO>>
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Etudiant trouvé"),
            @ApiResponse(responseCode = "404", description = "Etudiant introuvable",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EtudiantResponseDTO> getById(
            @Parameter(description = "Id de l'étudiant", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.getEtudiantById(id));
    }


    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Etudiant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<EtudiantResponseDTO> create(
            @Valid @RequestBody EtudiantRequestDTO dto) {
        // ↑ AVANT : @RequestBody Etudiant etudiant
        // ↑ APRÈS : @RequestBody EtudiantRequestDTO dto
        // Le client envoie le DTO — pas l'entité directement

        EtudiantResponseDTO nouveau = etudiantService.createEtudiant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifier un étudiant existant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Etudiant modifié"),
            @ApiResponse(responseCode = "404", description = "Etudiant introuvable")
    })
    public ResponseEntity<EtudiantResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantRequestDTO dto) {
        // ↑ AVANT : @RequestBody Etudiant etudiant
        // ↑ APRÈS : @RequestBody EtudiantRequestDTO dto

        return ResponseEntity.ok(etudiantService.updateEtudiant(id, dto));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Etudiant supprimé"),
            @ApiResponse(responseCode = "404", description = "Etudiant introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
        // ↑ pas de changement — delete ne retourne rien
    }
}