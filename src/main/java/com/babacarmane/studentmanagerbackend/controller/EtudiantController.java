package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.EtudiantRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.EtudiantResponseDTO;
import com.babacarmane.studentmanagerbackend.service.EtudiantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;


    @GetMapping
    public ResponseEntity<List<EtudiantResponseDTO>> getAll() {
        // ↑ AVANT : ResponseEntity<List<Etudiant>>
        // ↑ APRÈS : ResponseEntity<List<EtudiantResponseDTO>>
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }


    @GetMapping("/{id}")
    public ResponseEntity<EtudiantResponseDTO> getById(@PathVariable Long id) {
        // ↑ AVANT : ResponseEntity<Etudiant>
        // ↑ APRÈS : ResponseEntity<EtudiantResponseDTO>
        return ResponseEntity.ok(etudiantService.getEtudiantById(id));
    }


    @PostMapping
    public ResponseEntity<EtudiantResponseDTO> create(
            @Valid @RequestBody EtudiantRequestDTO dto) {
        // ↑ AVANT : @RequestBody Etudiant etudiant
        // ↑ APRÈS : @RequestBody EtudiantRequestDTO dto
        // Le client envoie le DTO — pas l'entité directement

        EtudiantResponseDTO nouveau = etudiantService.createEtudiant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EtudiantResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantRequestDTO dto) {
        // ↑ AVANT : @RequestBody Etudiant etudiant
        // ↑ APRÈS : @RequestBody EtudiantRequestDTO dto

        return ResponseEntity.ok(etudiantService.updateEtudiant(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
        // ↑ pas de changement — delete ne retourne rien
    }
}