package com.babacarmane.studentmanagerbackend.repository;

import com.babacarmane.studentmanagerbackend.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository <Etudiant, Long> {
}
