package com.babacarmane.studentmanagerbackend.service;

import com.babacarmane.studentmanagerbackend.dto.EtudiantRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.EtudiantResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Etudiant;
import com.babacarmane.studentmanagerbackend.exception.EtudiantNotFoundException;
import com.babacarmane.studentmanagerbackend.mapper.EtudiantMapper;
import com.babacarmane.studentmanagerbackend.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final EtudiantMapper etudiantMapper;
    // ↑ NOUVEAU — injecté grâce à @Component sur EtudiantMapper


    public Page<EtudiantResponseDTO> getAllEtudiants(Pageable pageable) {
        //     ↑ Page<T> = une page de résultats
        //                 contient les données + métadonnées
        //       Pageable = numéro de page, taille, tri

        Page<Etudiant> page = etudiantRepository.findAll(pageable);
        //                                               ↑ Spring fait le SQL automatiquement
        //   SELECT * FROM etudiants LIMIT 10 OFFSET 0

        return page.map(etudiantMapper::toResponseDTO);
        //           ↑ convertit chaque Etudiant en EtudiantResponseDTO
        //             en gardant la structure Page<T>
    }

    public List<EtudiantResponseDTO> getAllEtudiantsList() {
        return etudiantMapper.toResponseDTOList(etudiantRepository.findAll());
    }

//    public List<EtudiantResponseDTO> getAllEtudiants() {
//        List<Etudiant> etudiants = etudiantRepository.findAll();
//        return etudiantMapper.toResponseDTOList(etudiants);
//        // ↑ AVANT : retournait List<Etudiant> directement
//        //   APRÈS : passe par le Mapper → List<EtudiantResponseDTO>
//    }


    public EtudiantResponseDTO getEtudiantById(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EtudiantNotFoundException(id));
        return etudiantMapper.toResponseDTO(etudiant);
        // ↑ AVANT : retournait Etudiant directement
        //   APRÈS : passe par le Mapper → EtudiantResponseDTO
    }


    public EtudiantResponseDTO createEtudiant(EtudiantRequestDTO dto) {
        // ↑ AVANT : recevait Etudiant directement depuis le Controller
        //   APRÈS : reçoit EtudiantRequestDTO

        Etudiant etudiant = etudiantMapper.toEntity(dto);
        // ↑ NOUVEAU — Mapper convertit RequestDTO → Entité
        //   au lieu que le Controller envoie l'entité directement

        Etudiant saved = etudiantRepository.save(etudiant);
        return etudiantMapper.toResponseDTO(saved);
        // ↑ retourne EtudiantResponseDTO au lieu de Etudiant
    }


    public EtudiantResponseDTO updateEtudiant(Long id, EtudiantRequestDTO dto) {
        // ↑ AVANT : recevait Etudiant directement
        //   APRÈS : reçoit EtudiantRequestDTO

        Etudiant existant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EtudiantNotFoundException(id));

        // Met à jour champ par champ depuis le DTO
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setEmail(dto.getEmail());
        existant.setMatricule(dto.getMatricule());
        // ↑ AVANT : prenait les valeurs depuis etudiantModifie (entité)
        //   APRÈS : prend les valeurs depuis dto (RequestDTO)

        Etudiant saved = etudiantRepository.save(existant);
        return etudiantMapper.toResponseDTO(saved);
    }


    public void deleteEtudiant(Long id) {
        Etudiant existant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EtudiantNotFoundException(id));
        etudiantRepository.delete(existant);
        // ↑ pas de changement ici — delete ne retourne rien
    }
}