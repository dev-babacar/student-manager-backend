package com.babacarmane.studentmanagerbackend.service;

import com.babacarmane.studentmanagerbackend.dto.NoteRequestDTO;
import com.babacarmane.studentmanagerbackend.dto.NoteResponseDTO;
import com.babacarmane.studentmanagerbackend.entities.Cours;
import com.babacarmane.studentmanagerbackend.entities.Etudiant;
import com.babacarmane.studentmanagerbackend.entities.Note;
import com.babacarmane.studentmanagerbackend.exception.CoursNotFoundException;
import com.babacarmane.studentmanagerbackend.exception.EtudiantNotFoundException;
import com.babacarmane.studentmanagerbackend.exception.NoteDejaExistanteException;
import com.babacarmane.studentmanagerbackend.mapper.NoteMapper;
import com.babacarmane.studentmanagerbackend.repository.CoursRepository;
import com.babacarmane.studentmanagerbackend.repository.EtudiantRepository;
import com.babacarmane.studentmanagerbackend.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;
    private final NoteMapper noteMapper;
    // ↑ NOUVEAU — injecté grâce à @Component sur NoteMapper


    public Page<NoteResponseDTO> getAllNotes(Pageable pageable) {
        return noteRepository.findAll(pageable)
                .map(noteMapper::toResponseDTO);
    }


    public NoteResponseDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable : " + id));
        return noteMapper.toResponseDTO(note);
        // ↑ AVANT : retournait Note directement
        //   APRÈS : passe par le Mapper
    }


    public List<NoteResponseDTO> getNotesByEtudiant(Long etudiantId) {
        if (!etudiantRepository.existsById(etudiantId)) {
            throw new EtudiantNotFoundException(etudiantId);
        }
        List<Note> notes = noteRepository.findByEtudiantId(etudiantId);
        return noteMapper.toResponseDTOList(notes);
        // ↑ AVANT : retournait List<Note>
        //   APRÈS : retourne List<NoteResponseDTO>
    }


    public List<NoteResponseDTO> getNotesByCours(Long coursId) {
        if (!coursRepository.existsById(coursId)) {
            throw new CoursNotFoundException(coursId);
        }
        List<Note> notes = noteRepository.findByCoursId(coursId);
        return noteMapper.toResponseDTOList(notes);
    }


    @Transactional
    public NoteResponseDTO createNote(NoteRequestDTO dto) {
        Etudiant etudiant = etudiantRepository.findById(dto.getEtudiantId())
                .orElseThrow(() -> new EtudiantNotFoundException(dto.getEtudiantId()));

        Cours cours = coursRepository.findById(dto.getCoursId())
                .orElseThrow(() -> new CoursNotFoundException(dto.getCoursId()));

        if (noteRepository.existsByEtudiantIdAndCoursId(
                dto.getEtudiantId(), dto.getCoursId())) {
            throw new NoteDejaExistanteException(
                    dto.getEtudiantId(), dto.getCoursId());
        }

        Note note = Note.builder()
                .valeur(dto.getValeur())
                .etudiant(etudiant)
                .cours(cours)
                .build();

        Note saved = noteRepository.save(note);
        return noteMapper.toResponseDTO(saved);
        // ↑ AVANT : retournait Note directement
        //   APRÈS : passe par le Mapper → NoteResponseDTO
    }


    @Transactional
    public NoteResponseDTO updateNote(Long id, NoteRequestDTO dto) {
        Note existante = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable : " + id));

        if (dto.getEtudiantId() != null) {
            Etudiant etudiant = etudiantRepository.findById(dto.getEtudiantId())
                    .orElseThrow(() -> new EtudiantNotFoundException(dto.getEtudiantId()));
            existante.setEtudiant(etudiant);
        }

        if (dto.getCoursId() != null) {
            Cours cours = coursRepository.findById(dto.getCoursId())
                    .orElseThrow(() -> new CoursNotFoundException(dto.getCoursId()));
            existante.setCours(cours);
        }

        existante.setValeur(dto.getValeur());
        Note saved = noteRepository.save(existante);
        return noteMapper.toResponseDTO(saved);
        // ↑ AVANT : retournait Note directement
        //   APRÈS : passe par le Mapper
    }


    public void deleteNote(Long id) {
        Note existante = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable : " + id));
        noteRepository.delete(existante);
    }


    public Double getMoyenneByEtudiant(Long etudiantId) {
        if (!etudiantRepository.existsById(etudiantId)) {
            throw new EtudiantNotFoundException(etudiantId);
        }
        return noteRepository.findMoyenneByEtudiantId(etudiantId);
    }


    public Double getMoyenneByCours(Long coursId) {
        if (!coursRepository.existsById(coursId)) {
            throw new CoursNotFoundException(coursId);
        }
        return noteRepository.findMoyenneByCoursId(coursId);
    }
}