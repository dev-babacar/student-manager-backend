package com.babacarmane.studentmanagerbackend.controller;

import com.babacarmane.studentmanagerbackend.dto.UserResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.User;
import com.babacarmane.studentmanagerbackend.enums.Role;
import com.babacarmane.studentmanagerbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// controller/UserController.java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {

    private final UserRepository userRepository;

    // GET /api/users — voir tous les utilisateurs (ADMIN seulement)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister tous les utilisateurs — ADMIN seulement")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(u -> UserResponseDTO.builder()
                        .id(u.getId())
                        .nom(u.getNom())
                        .email(u.getEmail())
                        .role(u.getRole().name())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // PUT /api/users/1/role?role=ADMIN — changer le rôle (ADMIN seulement)
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Changer le rôle d'un utilisateur — ADMIN seulement")
    public ResponseEntity<String> updateRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + id));

        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok("Rôle mis à jour → " + role + " pour " + user.getEmail());
    }
}
