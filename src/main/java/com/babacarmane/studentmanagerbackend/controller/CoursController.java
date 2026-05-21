package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.CoursRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.CoursResponseDTO;
import com.babacarmane.studentmanagerbackend.service.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursService coursService;


    @GetMapping
    public ResponseEntity<List<CoursResponseDTO>> getAllCours() {
        // ↑ AVANT : ResponseEntity<List<Cours>>
        // ↑ APRÈS : ResponseEntity<List<CoursResponseDTO>>
        return ResponseEntity.ok(coursService.getAllCours());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CoursResponseDTO> getById(@PathVariable Long id) {
        // ↑ AVANT : manquait dans ton Controller — ajouté
        return ResponseEntity.ok(coursService.getCoursById(id));
    }


    @PostMapping
    public ResponseEntity<CoursResponseDTO> create(
            @Valid @RequestBody CoursRequestDTO dto) {
        // ↑ AVANT : @RequestBody Cours cours
        // ↑ APRÈS : @RequestBody CoursRequestDTO dto

        CoursResponseDTO nouveau = coursService.createCours(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CoursResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CoursRequestDTO dto) {
        // ↑ AVANT : @RequestBody Cours cours
        // ↑ APRÈS : @RequestBody CoursRequestDTO dto

        return ResponseEntity.ok(coursService.updateCours(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }
}