package com.ahmedbenidar.banque.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ahmedbenidar.banque.domain.Compte;

import java.util.ArrayList;
import java.util.List;

/**
 * Enveloppe de collection : une {@code List} nue n'a pas de racine XML.
 * L'élément XML s'appelle {@code compte}, exactement comme la clé JSON.
 */
@JacksonXmlRootElement(localName = "comptes")
public class Comptes {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "compte")
    private List<Compte> compte = new ArrayList<>();

    public Comptes() {
    }

    public Comptes(List<Compte> comptes) {
        this.compte = comptes == null ? new ArrayList<>() : new ArrayList<>(comptes);
    }

    public static Comptes de(List<Compte> comptes) {
        return new Comptes(comptes);
    }

    public List<Compte> getCompte() {
        return compte;
    }

    public void setCompte(List<Compte> compte) {
        this.compte = compte;
    }
}
