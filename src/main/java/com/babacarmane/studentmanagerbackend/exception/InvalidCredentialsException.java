package com.babacarmane.studentmanagerbackend.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Le mot de passe que vous avez saisi est incorrecte");
    }
}
