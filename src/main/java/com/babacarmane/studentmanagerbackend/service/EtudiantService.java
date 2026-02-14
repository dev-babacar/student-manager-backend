package com.babacarmane.studentmanagerbackend.service;

import com.babacarmane.studentmanagerbackend.model.Etudiant;
import com.babacarmane.studentmanagerbackend.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {
    private final EtudiantRepository etudiantRepository;

    public List<Etudiant> getAllEtudiants(){
        return etudiantRepository.findAll();
    }

    public Etudiant createEtudiant(Etudiant e){
        return etudiantRepository.save(e);
    }
}
