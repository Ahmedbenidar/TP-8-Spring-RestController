package com.ahmedbenidar.banque.domain;

/**
 * Nature du compte bancaire. Stockée en base sous forme de chaîne
 * ({@code EnumType.STRING}) pour rester lisible dans la console H2.
 */
public enum TypeCompte {
    COURANT,
    EPARGNE,
    PROFESSIONNEL
}
