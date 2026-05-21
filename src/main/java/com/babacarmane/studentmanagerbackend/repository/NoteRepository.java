package com.babacarmane.studentmanagerbackend.repository;

import com.babacarmane.studentmanagerbackend.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n " +
            "LEFT JOIN FETCH n.etudiant " +
            "LEFT JOIN FETCH n.cours " +
            "WHERE n.etudiant.id = :etudiantId")
    List<Note> findByEtudiantId( @Param("etudiantId") Long etudiantId);

    @Query("SELECT n FROM Note n " +
            "LEFT JOIN FETCH n.etudiant " +
            "LEFT JOIN FETCH n.cours " +
            "WHERE n.cours.id = :coursId")
    List<Note> findByCoursId ( @Param("coursId") Long coursId);

    // ─────────────────────────────────────────────────────────
    // BESOIN 1 — Vérifier si une note existe déjà
    // avant d'en créer une (ton @UniqueConstraint en dépend)
    // ─────────────────────────────────────────────────────────
    boolean existsByEtudiantIdAndCoursId(Long etudiantId, Long coursId);

    // ─────────────────────────────────────────────────────────
    // BESOIN 2 — Modifier une note existante
    // tu as besoin de la retrouver par étudiant + cours
    // ─────────────────────────────────────────────────────────
    @Query("SELECT n FROM Note n " +
            "LEFT JOIN FETCH n.etudiant " +
                "LEFT JOIN FETCH n.cours " +
            "WHERE n.etudiant.id = :etudiantId " +
            "AND n.cours.id = :coursId")
    Optional<Note> findByEtudiantIdAndCoursId(
            @Param("etudiantId") Long etudiantId,
            @Param("coursId") Long coursId
    );

    // ─────────────────────────────────────────────────────────
    // BESOIN 3 — Supprimer les notes d'un étudiant
    // ─────────────────────────────────────────────────────────

    // Pas besoin de @Query — Spring comprend le nom
    // (CascadeType.ALL dans Etudiant gère aussi la suppression
    //  mais cette méthode est utile si tu veux supprimer
    //  les notes SANS supprimer l'étudiant)
    void deleteByEtudiantId(Long etudiantId);

    // ─────────────────────────────────────────────────────────
    // BESOIN 4 — Chercher les notes entre deux valeurs
    // ─────────────────────────────────────────────────────────
    // Pas besoin de @Query — Spring comprend le nom
    List<Note> findByValeurBetween(double min, double max);

    // ─────────────────────────────────────────────────────────
    // BESOIN 5 — Moyenne d'un étudiant ou d'un cours
    // Calcul → obligatoirement @Query
    // ─────────────────────────────────────────────────────────
    @Query("SELECT AVG(n.valeur) FROM Note n " +
            "WHERE n.etudiant.id = :etudiantId")
    Double findMoyenneByEtudiantId(@Param("etudiantId") Long etudiantId);

    @Query("SELECT AVG(n.valeur) FROM Note n " +
            "WHERE n.cours.id = :coursId")
    Double findMoyenneByCoursId(@Param("coursId") Long coursId);
}
        