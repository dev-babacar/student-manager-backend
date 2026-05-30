package com.babacarmane.studentmanagerbackend.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {
    // ─────────────────────────────────────────
    // 404 — Etudiant pas trouvé
    // ─────────────────────────────────────────
    @ExceptionHandler(EtudiantNotFoundException.class)
    // ↑ Quand EtudiantNotFoundException est lancée
    //   n'importe où → cette méthode est appelée
    public ResponseEntity<ErrorResponse> handleEtudiantNotFound(
            EtudiantNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage()               // "Etudiant non trouvé avec l'id : 99"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    // ─────────────────────────────────────────
    // 404 — Cours pas trouvé
    // ─────────────────────────────────────────
    @ExceptionHandler(CoursNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCoursNotFound(
            CoursNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    // ─────────────────────────────────────────
    // 409 — Note déjà existante
    // ─────────────────────────────────────────
    @ExceptionHandler(NoteDejaExistanteException.class)
    public ResponseEntity<ErrorResponse> handleNoteDejaExistante(
            NoteDejaExistanteException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(), // 409
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    // ─────────────────────────────────────────
    // 400 — Validation échouée (@Valid)
    // Lancée automatiquement quand @NotBlank @Email etc. échouent
    // ─────────────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        // Récupère le premier message d'erreur de validation
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        // → "nom : Le nom est obligatoire, email : Email invalide"

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400
                message
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    // ─────────────────────────────────────────
    // 500 — Toute autre exception imprévue
    // Filet de sécurité final
    // ─────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Une erreur interne est survenue"
                // ↑ jamais exposer ex.getMessage() ici
                //   ça pourrait révéler des détails sensibles
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    // ─────────────────────────────────────
    // 401 — Mauvais email ou mot de passe
    // ─────────────────────────────────────
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, ex.getMessage()));
    }

    // ─────────────────────────────────────
    // 401 — BadCredentialsException de Spring
    //       filet de sécurité si elle remonte quand même
    // ─────────────────────────────────────
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, "Email ou mot de passe incorrect"));
    }
}
