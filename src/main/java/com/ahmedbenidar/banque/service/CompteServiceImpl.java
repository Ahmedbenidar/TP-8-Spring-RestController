package com.ahmedbenidar.banque.service;

import com.ahmedbenidar.banque.domain.Compte;
import com.ahmedbenidar.banque.domain.TypeCompte;
import com.ahmedbenidar.banque.dto.StatistiquesBanque;
import com.ahmedbenidar.banque.exception.CompteIntrouvableException;
import com.ahmedbenidar.banque.repository.CompteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public List<Compte> listerComptes() {
        return compteRepository.findAll();
    }

    @Override
    public List<Compte> listerParType(TypeCompte type) {
        return compteRepository.findByType(type);
    }

    @Override
    public List<Compte> rechercherParIntervalleDeSolde(BigDecimal min, BigDecimal max) {
        BigDecimal borneBasse = min == null ? BigDecimal.ZERO : min;
        BigDecimal borneHaute = max == null ? new BigDecimal("999999999") : max;
        if (borneBasse.compareTo(borneHaute) > 0) {
            throw new IllegalArgumentException(
                    "Le solde minimum (" + borneBasse + ") depasse le solde maximum (" + borneHaute + ")");
        }
        return compteRepository.findBySoldeBetweenOrderBySoldeAsc(borneBasse, borneHaute);
    }

    @Override
    public List<Compte> rechercherParTitulaire(String fragment) {
        if (fragment == null || fragment.isBlank()) {
            throw new IllegalArgumentException("Le parametre titulaire ne peut pas etre vide");
        }
        return compteRepository.findByTitulaireContainingIgnoreCase(fragment.trim());
    }

    @Override
    public List<Compte> listerActifsTriesParSolde() {
        return compteRepository.findByActifTrueOrderBySoldeDesc();
    }

    @Override
    public Compte trouverParId(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> CompteIntrouvableException.parId(id));
    }

    @Override
    public Compte trouverParNumero(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte)
                .orElseThrow(() -> CompteIntrouvableException.parNumero(numeroCompte));
    }

    @Override
    @Transactional
    public Compte creer(Compte compte) {
        if (compte.getNumeroCompte() == null || compte.getNumeroCompte().isBlank()) {
            compte.setNumeroCompte(genererNumero());
        } else if (compteRepository.existsByNumeroCompte(compte.getNumeroCompte())) {
            throw new IllegalArgumentException(
                    "La reference " + compte.getNumeroCompte() + " est deja utilisee");
        }
        if (compte.getDateCreation() == null) {
            compte.setDateCreation(LocalDate.now());
        }
        if (compte.getDevise() == null || compte.getDevise().isBlank()) {
            compte.setDevise("MAD");
        }
        if (compte.getAgence() == null || compte.getAgence().isBlank()) {
            compte.setAgence("Agence Centrale");
        }
        compte.setSolde(arrondir(compte.getSolde()));
        compte.setId(null);
        return compteRepository.save(compte);
    }

    @Override
    @Transactional
    public Compte remplacer(Long id, Compte compte) {
        Compte existant = trouverParId(id);
        existant.setTitulaire(compte.getTitulaire());
        existant.setAgence(compte.getAgence());
        existant.setSolde(arrondir(compte.getSolde()));
        existant.setDevise(compte.getDevise());
        existant.setType(compte.getType());
        existant.setActif(compte.isActif());
        if (compte.getDateCreation() != null) {
            existant.setDateCreation(compte.getDateCreation());
        }
        return compteRepository.save(existant);
    }

    @Override
    @Transactional
    public Compte changerStatut(Long id, boolean actif) {
        Compte existant = trouverParId(id);
        existant.setActif(actif);
        return compteRepository.save(existant);
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        compteRepository.delete(trouverParId(id));
    }

    @Override
    public StatistiquesBanque statistiques() {
        List<Compte> tous = compteRepository.findAll();
        BigDecimal total = tous.stream()
                .map(Compte::getSolde)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal maximum = tous.stream()
                .map(Compte::getSolde)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal moyen = tous.isEmpty()
                ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(tous.size()), 2, RoundingMode.HALF_UP);
        return new StatistiquesBanque(
                tous.size(),
                compteRepository.countByType(TypeCompte.COURANT),
                compteRepository.countByType(TypeCompte.EPARGNE),
                compteRepository.countByType(TypeCompte.PROFESSIONNEL),
                arrondir(total),
                moyen,
                arrondir(maximum),
                arrondir(compteRepository.totalSoldeParType(TypeCompte.COURANT)),
                arrondir(compteRepository.totalSoldeParType(TypeCompte.EPARGNE)),
                arrondir(compteRepository.totalSoldeParType(TypeCompte.PROFESSIONNEL)));
    }

    private static BigDecimal arrondir(BigDecimal valeur) {
        return valeur == null ? BigDecimal.ZERO : valeur.setScale(2, RoundingMode.HALF_UP);
    }

    private static String genererNumero() {
        int alea = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "BQ-" + LocalDate.now().getYear() + "-" + alea;
    }
}
