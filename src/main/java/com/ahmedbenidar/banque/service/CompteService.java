package com.ahmedbenidar.banque.service;

import com.ahmedbenidar.banque.domain.Compte;
import com.ahmedbenidar.banque.domain.TypeCompte;
import com.ahmedbenidar.banque.dto.StatistiquesBanque;

import java.math.BigDecimal;
import java.util.List;

/** Contrat métier : la couche web ne connaît ni JPA ni Hibernate. */
public interface CompteService {

    List<Compte> listerComptes();

    List<Compte> listerParType(TypeCompte type);

    List<Compte> rechercherParIntervalleDeSolde(BigDecimal min, BigDecimal max);

    List<Compte> rechercherParTitulaire(String fragment);

    List<Compte> listerActifsTriesParSolde();

    Compte trouverParId(Long id);

    Compte trouverParNumero(String numeroCompte);

    Compte creer(Compte compte);

    Compte remplacer(Long id, Compte compte);

    Compte changerStatut(Long id, boolean actif);

    void supprimer(Long id);

    StatistiquesBanque statistiques();
}
