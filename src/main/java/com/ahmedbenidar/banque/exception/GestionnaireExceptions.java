package com.ahmedbenidar.banque.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Gestion centralisée des erreurs : toutes les réponses d'erreur ont la même
 * forme en JSON qu'en XML, avec le bon code HTTP.
 */
@RestControllerAdvice
public class GestionnaireExceptions {

    @ExceptionHandler(CompteIntrouvableException.class)
    public ResponseEntity<ErreurApi> compteIntrouvable(CompteIntrouvableException ex,
                                                       HttpServletRequest requete) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErreurApi(
                HttpStatus.NOT_FOUND.value(),
                "COMPTE_INTROUVABLE",
                ex.getMessage(),
                requete.getRequestURI(),
                Instant.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurApi> validation(MethodArgumentNotValidException ex,
                                                HttpServletRequest requete) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(" ; "));
        return ResponseEntity.badRequest().body(new ErreurApi(
                HttpStatus.BAD_REQUEST.value(),
                "DONNEES_INVALIDES",
                details,
                requete.getRequestURI(),
                Instant.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErreurApi> argumentInvalide(IllegalArgumentException ex,
                                                      HttpServletRequest requete) {
        return ResponseEntity.badRequest().body(new ErreurApi(
                HttpStatus.BAD_REQUEST.value(),
                "ARGUMENT_INVALIDE",
                ex.getMessage(),
                requete.getRequestURI(),
                Instant.now()));
    }
}
