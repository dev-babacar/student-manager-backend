package com.babacarmane.studentmanagerbackend.config;

import com.babacarmane.studentmanagerbackend.entities.Cours;
import com.babacarmane.studentmanagerbackend.entities.Etudiant;
import com.babacarmane.studentmanagerbackend.entities.Note;
import com.babacarmane.studentmanagerbackend.repository.CoursRepository;
import com.babacarmane.studentmanagerbackend.repository.EtudiantRepository;
import com.babacarmane.studentmanagerbackend.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// config/DataInitializer.java
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;
    private final NoteRepository noteRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // Si des données existent déjà → on ne fait rien
        if (etudiantRepository.count() > 0) {
            log.info("✅ Données déjà présentes — initialisation ignorée");
            return;
        }

        log.info("🚀 Insertion des données de test...");

        // ─────────────────────────────────────
        // 1. COURS — créer d'abord car Note en dépend
        // ─────────────────────────────────────
        Cours maths = Cours.builder()
                .nom("Mathématiques").code("MATH101").coefficient(3).build();
        Cours anglais = Cours.builder()
                .nom("Anglais").code("ANGL101").coefficient(2).build();
        Cours physique = Cours.builder()
                .nom("Physique").code("PHYS101").coefficient(3).build();
        Cours info = Cours.builder()
                .nom("Informatique").code("INFO101").coefficient(4).build();
        Cours français = Cours.builder()
                .nom("Français").code("FRAN101").coefficient(2).build();

        List<Cours> coursList = coursRepository.saveAll(
                List.of(maths, anglais, physique, info, français)
        );
        log.info("✅ {} cours insérés", coursList.size());


        // ─────────────────────────────────────
        // 2. ETUDIANTS
        // ─────────────────────────────────────
        Etudiant alice  = Etudiant.builder().nom("Diop").prenom("Alice").email("alice@mail.com").matricule("ETU001").build();
        Etudiant bob    = Etudiant.builder().nom("Sall").prenom("Bob").email("bob@mail.com").matricule("ETU002").build();
        Etudiant carol  = Etudiant.builder().nom("Ndiaye").prenom("Carol").email("carol@mail.com").matricule("ETU003").build();
        Etudiant david  = Etudiant.builder().nom("Fall").prenom("David").email("david@mail.com").matricule("ETU004").build();
        Etudiant eva    = Etudiant.builder().nom("Mbaye").prenom("Eva").email("eva@mail.com").matricule("ETU005").build();
        Etudiant frank  = Etudiant.builder().nom("Diouf").prenom("Frank").email("frank@mail.com").matricule("ETU006").build();
        Etudiant grace  = Etudiant.builder().nom("Thiam").prenom("Grace").email("grace@mail.com").matricule("ETU007").build();
        Etudiant henri  = Etudiant.builder().nom("Ba").prenom("Henri").email("henri@mail.com").matricule("ETU008").build();
        Etudiant ines   = Etudiant.builder().nom("Sy").prenom("Ines").email("ines@mail.com").matricule("ETU009").build();
        Etudiant jules  = Etudiant.builder().nom("Kane").prenom("Jules").email("jules@mail.com").matricule("ETU010").build();
        Etudiant kara   = Etudiant.builder().nom("Cisse").prenom("Kara").email("kara@mail.com").matricule("ETU011").build();
        Etudiant lamine = Etudiant.builder().nom("Toure").prenom("Lamine").email("lamine@mail.com").matricule("ETU012").build();
        Etudiant marie  = Etudiant.builder().nom("Gueye").prenom("Marie").email("marie@mail.com").matricule("ETU013").build();
        Etudiant nadia  = Etudiant.builder().nom("Sarr").prenom("Nadia").email("nadia@mail.com").matricule("ETU014").build();
        Etudiant omar   = Etudiant.builder().nom("Faye").prenom("Omar").email("omar@mail.com").matricule("ETU015").build();

        List<Etudiant> etudiantList = etudiantRepository.saveAll(List.of(
                alice, bob, carol, david, eva,
                frank, grace, henri, ines, jules,
                kara, lamine, marie, nadia, omar
        ));
        log.info("✅ {} étudiants insérés", etudiantList.size());


        // ─────────────────────────────────────
        // 3. NOTES — après étudiants et cours
        // ─────────────────────────────────────
        List<Note> notes = List.of(
                // Alice
                Note.builder().valeur(15.5).etudiant(alice).cours(maths).build(),
                Note.builder().valeur(14.0).etudiant(alice).cours(anglais).build(),
                Note.builder().valeur(17.0).etudiant(alice).cours(info).build(),

                // Bob
                Note.builder().valeur(12.0).etudiant(bob).cours(maths).build(),
                Note.builder().valeur(11.5).etudiant(bob).cours(physique).build(),
                Note.builder().valeur(13.0).etudiant(bob).cours(français).build(),

                // Carol
                Note.builder().valeur(18.0).etudiant(carol).cours(maths).build(),
                Note.builder().valeur(16.5).etudiant(carol).cours(anglais).build(),
                Note.builder().valeur(19.0).etudiant(carol).cours(info).build(),

                // David
                Note.builder().valeur(9.0).etudiant(david).cours(maths).build(),
                Note.builder().valeur(10.5).etudiant(david).cours(physique).build(),

                // Eva
                Note.builder().valeur(14.5).etudiant(eva).cours(anglais).build(),
                Note.builder().valeur(13.5).etudiant(eva).cours(français).build(),

                // Frank
                Note.builder().valeur(11.0).etudiant(frank).cours(maths).build(),
                Note.builder().valeur(12.5).etudiant(frank).cours(info).build(),

                // Grace
                Note.builder().valeur(16.0).etudiant(grace).cours(physique).build(),
                Note.builder().valeur(15.0).etudiant(grace).cours(maths).build(),

                // Henri
                Note.builder().valeur(8.5).etudiant(henri).cours(anglais).build(),
                Note.builder().valeur(9.5).etudiant(henri).cours(français).build(),

                // Ines
                Note.builder().valeur(17.5).etudiant(ines).cours(info).build(),
                Note.builder().valeur(16.0).etudiant(ines).cours(maths).build(),

                // Jules
                Note.builder().valeur(13.0).etudiant(jules).cours(physique).build(),
                Note.builder().valeur(14.0).etudiant(jules).cours(anglais).build()
        );

        noteRepository.saveAll(notes);
        log.info("✅ {} notes insérées", notes.size());

        log.info("🎉 Initialisation terminée — base prête pour les tests !");
    }
}
