package com.babacarmane.studentmanagerbackend.exception;

public class NoteDejaExistanteException extends RuntimeException{

    public NoteDejaExistanteException(Long etudiantId, Long coursId){
        super("Une note existe déjà pour l'étudiant " +
                etudiantId + " dans le cours " + coursId);
    }
}
