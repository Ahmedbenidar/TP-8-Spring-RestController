package com.ahmedbenidar.banque.exception;

/** Levée quand aucun compte ne correspond à l'identifiant ou à la référence demandée. */
public class CompteIntrouvableException extends RuntimeException {

    public CompteIntrouvableException(String message) {
        super(message);
    }

    public static CompteIntrouvableException parId(Long id) {
        return new CompteIntrouvableException(
                "Aucun compte bancaire ne correspond a l'identifiant " + id);
    }

    public static CompteIntrouvableException parNumero(String numero) {
        return new CompteIntrouvableException(
                "Aucun compte bancaire ne porte la reference " + numero);
    }
}
