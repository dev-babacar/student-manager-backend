package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.CoursRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.CoursResponseDTO;
import com.babacarmane.studentmanagerbackend.exception.ErrorResponse;
import com.babacarmane.studentmanagerbackend.service.CoursService;
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
@RequestMapping("/api/cours")
@RequiredArgsConstructor
@Tag(name = "Cours", description = "Gestion des cours")
public class CoursController {

    private final CoursService coursService;


    @GetMapping
    @Operation(
            summary = "Lister tous les cours",
            description = "Retourne la liste complète de tous les cours"
    )
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    public ResponseEntity<Page<CoursResponseDTO>> getAllCours(
            @PageableDefault(page = 0, size = 10, sort = "nom")
            Pageable pageable) {
        return ResponseEntity.ok(coursService.getAllCours(pageable));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un cours par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cours trouvé"),
            @ApiResponse(responseCode = "404", description = "Cours introuvable",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CoursResponseDTO> getById(@PathVariable Long id) {
        // ↑ AVANT : manquait dans ton Controller — ajouté
        return ResponseEntity.ok(coursService.getCoursById(id));
    }


    @PostMapping
    @Operation(summary = "Créer un nouveau cours")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cours créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<CoursResponseDTO> create(
            @Valid @RequestBody CoursRequestDTO dto) {
        // ↑ AVANT : @RequestBody Cours cours
        // ↑ APRÈS : @RequestBody CoursRequestDTO dto

        CoursResponseDTO nouveau = coursService.createCours(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifier un cours existant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cours modifié"),
            @ApiResponse(responseCode = "404", description = "Cours introuvable")
    })
    public ResponseEntity<CoursResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CoursRequestDTO dto) {
        // ↑ AVANT : @RequestBody Cours cours
        // ↑ APRÈS : @RequestBody CoursRequestDTO dto

        return ResponseEntity.ok(coursService.updateCours(id, dto));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un cours")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cours supprimé"),
            @ApiResponse(responseCode = "404", description = "Cours introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }
}