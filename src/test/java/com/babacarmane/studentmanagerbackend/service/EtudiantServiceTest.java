package com.babacarmane.studentmanagerbackend.service;

import com.babacarmane.studentmanagerbackend.dto.EtudiantRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.EtudiantResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Etudiant;
import com.babacarmane.studentmanagerbackend.exception.EtudiantNotFoundException;
import com.babacarmane.studentmanagerbackend.mapper.EtudiantMapper;
import com.babacarmane.studentmanagerbackend.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    // ─────────────────────────────────────
    // Faux objets — ne vont jamais en base
    // ─────────────────────────────────────
    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private EtudiantMapper etudiantMapper;

    // ─────────────────────────────────────
    // Vrai Service — avec les faux objets injectés
    // ─────────────────────────────────────
    @InjectMocks
    private EtudiantService etudiantService;


    // ─────────────────────────────────────
    // Données de test réutilisables
    // ─────────────────────────────────────
    private Etudiant etudiant;
    private EtudiantResponseDTO responseDTO;
    private EtudiantRequestDTO requestDTO;

    @BeforeEach
        // ↑ Exécuté AVANT chaque test
        //   Recrée les objets proprement pour chaque test
    void setUp() {
        etudiant = Etudiant.builder()
                .id(1L)
                .nom("Diop")
                .prenom("Alice")
                .email("alice@mail.com")
                .matricule("ETU001")
                .build();

        responseDTO = EtudiantResponseDTO.builder()
                .id(1L)
                .nom("Diop")
                .prenom("Alice")
                .email("alice@mail.com")
                .matricule("ETU001")
                .build();

        requestDTO = EtudiantRequestDTO.builder()
                .nom("Diop")
                .prenom("Alice")
                .email("alice@mail.com")
                .matricule("ETU001")
                .build();
    }


    // ══════════════════════════════════════
    // TEST 1 — getEtudiantById() — succès
    // ══════════════════════════════════════
    @Test
    @DisplayName("getEtudiantById — doit retourner le DTO quand l'étudiant existe")
    void getEtudiantById_WhenExists_ShouldReturnDTO() {

        // GIVEN — on prépare les fausses réponses
        when(etudiantRepository.findById(1L))
                .thenReturn(Optional.of(etudiant));
        // ↑ "quand findById(1) est appelé → retourne cet étudiant"

        when(etudiantMapper.toResponseDTO(etudiant))
                .thenReturn(responseDTO);
        // ↑ "quand toResponseDTO(etudiant) est appelé → retourne ce DTO"


        // WHEN — on appelle la vraie méthode
        EtudiantResponseDTO result = etudiantService.getEtudiantById(1L);


        // THEN — on vérifie le résultat
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNom()).isEqualTo("Diop");
        assertThat(result.getEmail()).isEqualTo("alice@mail.com");

        // Vérifie que findById a été appelé exactement 1 fois avec l'id 1
        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantMapper, times(1)).toResponseDTO(etudiant);
    }


    // ══════════════════════════════════════
    // TEST 2 — getEtudiantById() — étudiant introuvable
    // ══════════════════════════════════════
    @Test
    @DisplayName("getEtudiantById — doit lancer EtudiantNotFoundException si inexistant")
    void getEtudiantById_WhenNotExists_ShouldThrowException() {

        // GIVEN
        when(etudiantRepository.findById(999L))
                .thenReturn(Optional.empty());
        // ↑ "quand findById(999) est appelé → retourne vide"


        // WHEN + THEN — vérifie que l'exception est lancée
        assertThatThrownBy(() -> etudiantService.getEtudiantById(999L))
                .isInstanceOf(EtudiantNotFoundException.class);
        // ↑ "l'appel doit lancer EtudiantNotFoundException"

        // Vérifie que le mapper n'a JAMAIS été appelé
        // (pas de conversion si étudiant inexistant)
        verify(etudiantMapper, never()).toResponseDTO(any());
    }


    // ══════════════════════════════════════
    // TEST 3 — getAllEtudiants()
    // ══════════════════════════════════════
    @Test
    @DisplayName("getAllEtudiants — doit retourner la liste des DTOs")
    void getAllEtudiants_ShouldReturnDTOList() {

        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        // ↑ crée un Pageable : page 0, taille 10

        Page<Etudiant> page = new PageImpl<>(List.of(etudiant));
        // ↑ PageImpl = implémentation de Page pour les tests
        //   simule une page avec un seul étudiant dedans

        when(etudiantRepository.findAll(pageable))
                .thenReturn(page);
        // ↑ "quand findAll(pageable) est appelé → retourne cette page"

        when(etudiantMapper.toResponseDTO(etudiant))
                .thenReturn(responseDTO);


        // WHEN
        Page<EtudiantResponseDTO> result = etudiantService.getAllEtudiants(pageable);


        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNom()).isEqualTo("Diop");

        verify(etudiantRepository, times(1)).findAll(pageable);
    }

    // ══════════════════════════════════════
    // TEST 4 — createEtudiant()
    // ══════════════════════════════════════
    @Test
    @DisplayName("createEtudiant — doit sauvegarder et retourner le DTO")
    void createEtudiant_ShouldSaveAndReturnDTO() {

        // GIVEN
        when(etudiantMapper.toEntity(requestDTO))
                .thenReturn(etudiant);
        // ↑ RequestDTO → Entité via le Mapper

        when(etudiantRepository.save(etudiant))
                .thenReturn(etudiant);
        // ↑ save() retourne l'entité sauvegardée

        when(etudiantMapper.toResponseDTO(etudiant))
                .thenReturn(responseDTO);
        // ↑ Entité → ResponseDTO via le Mapper


        // WHEN
        EtudiantResponseDTO result = etudiantService.createEtudiant(requestDTO);


        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Diop");
        assertThat(result.getEmail()).isEqualTo("alice@mail.com");

        // Vérifie que save a bien été appelé
        verify(etudiantRepository, times(1)).save(etudiant);
    }


    // ══════════════════════════════════════
    // TEST 5 — deleteEtudiant() — succès
    // ══════════════════════════════════════
    @Test
    @DisplayName("deleteEtudiant — doit supprimer l'étudiant existant")
    void deleteEtudiant_WhenExists_ShouldDelete() {

        // GIVEN
        when(etudiantRepository.findById(1L))
                .thenReturn(Optional.of(etudiant));


        // WHEN
        etudiantService.deleteEtudiant(1L);


        // THEN
        // Vérifie que delete a été appelé avec le bon étudiant
        verify(etudiantRepository, times(1)).delete(etudiant);
    }


    // ══════════════════════════════════════
    // TEST 6 — deleteEtudiant() — inexistant
    // ══════════════════════════════════════
    @Test
    @DisplayName("deleteEtudiant — doit lancer exception si étudiant inexistant")
    void deleteEtudiant_WhenNotExists_ShouldThrowException() {

        // GIVEN
        when(etudiantRepository.findById(999L))
                .thenReturn(Optional.empty());


        // WHEN + THEN
        assertThatThrownBy(() -> etudiantService.deleteEtudiant(999L))
                .isInstanceOf(EtudiantNotFoundException.class);

        // Vérifie que delete n'a JAMAIS été appelé
        verify(etudiantRepository, never()).delete(any());
    }


    // ══════════════════════════════════════
    // TEST 7 — updateEtudiant()
    // ══════════════════════════════════════
    @Test
    @DisplayName("updateEtudiant — doit modifier et retourner le DTO mis à jour")
    void updateEtudiant_WhenExists_ShouldUpdateAndReturn() {

        // GIVEN
        EtudiantRequestDTO updateDTO = EtudiantRequestDTO.builder()
                .nom("Diop Modifié")
                .prenom("Alice")
                .email("alice.new@mail.com")
                .matricule("ETU001")
                .build();

        EtudiantResponseDTO updatedResponse = EtudiantResponseDTO.builder()
                .id(1L)
                .nom("Diop Modifié")
                .prenom("Alice")
                .email("alice.new@mail.com")
                .build();

        when(etudiantRepository.findById(1L))
                .thenReturn(Optional.of(etudiant));

        when(etudiantRepository.save(any(Etudiant.class)))
                .thenReturn(etudiant);

        when(etudiantMapper.toResponseDTO(any(Etudiant.class)))
                .thenReturn(updatedResponse);


        // WHEN
        EtudiantResponseDTO result = etudiantService.updateEtudiant(1L, updateDTO);


        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Diop Modifié");
        assertThat(result.getEmail()).isEqualTo("alice.new@mail.com");

        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }
}