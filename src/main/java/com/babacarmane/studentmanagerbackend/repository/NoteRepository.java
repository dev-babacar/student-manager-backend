package com.babacarmane.studentmanagerbackend.repository;

import com.babacarmane.studentmanagerbackend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
