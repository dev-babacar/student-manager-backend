package com.babacarmane.studentmanagerbackend.exception;

public class EtudiantNotFoundException extends RuntimeException{

    public EtudiantNotFoundException(Long id) {
        super("Etudiant non trouvé avec l'id : " + id);
        //     ↑ le message qui sera dans la réponse JSON
    }

}
