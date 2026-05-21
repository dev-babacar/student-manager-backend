package com.babacarmane.studentmanagerbackend.repository;

import com.babacarmane.studentmanagerbackend.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository <Etudiant, Long> {

}
