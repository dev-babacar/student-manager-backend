package com.babacarmane.studentmanagerbackend.repository;

import com.babacarmane.studentmanagerbackend.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}
