package com.ahmedbenidar.banque;

import com.ahmedbenidar.banque.domain.Compte;
import com.ahmedbenidar.banque.domain.TypeCompte;
import com.ahmedbenidar.banque.repository.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class BanqueRestControllerApplication {

    private static final Logger trace = LoggerFactory.getLogger(BanqueRestControllerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BanqueRestControllerApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserComptes(CompteRepository compteRepository) {
        return args -> {
            if (compteRepository.count() > 0) {
                trace.info("Jeu de donnees deja present, initialisation ignoree");
                return;
            }
            List<Compte> comptes = List.of(
                    Compte.builder()
                            .numeroCompte("BQ-2026-000101").titulaire("Nadia El Amrani")
                            .agence("Casablanca Centre").solde(new BigDecimal("12500.75"))
                            .devise("MAD").dateCreation(LocalDate.of(2024, 1, 15))
                            .type(TypeCompte.COURANT).actif(true).build(),
                    Compte.builder()
                            .numeroCompte("BQ-2026-000102").titulaire("Youssef Bennis")
                            .agence("Rabat Agdal").solde(new BigDecimal("48200.00"))
                            .devise("MAD").dateCreation(LocalDate.of(2023, 11, 2))
                            .type(TypeCompte.EPARGNE).actif(true).build(),
                    Compte.builder()
                            .numeroCompte("BQ-2026-000103").titulaire("Salma Ouazzani")
                            .agence("Marrakech Gueliz").solde(new BigDecimal("7300.40"))
                            .devise("MAD").dateCreation(LocalDate.of(2024, 3, 21))
                            .type(TypeCompte.COURANT).actif(true).build(),
                    Compte.builder()
                            .numeroCompte("BQ-2026-000104").titulaire("Karim Tazi")
                            .agence("Fes Ville Nouvelle").solde(new BigDecimal("15600.00"))
                            .devise("MAD").dateCreation(LocalDate.of(2024, 6, 9))
                            .type(TypeCompte.EPARGNE).actif(false).build(),
                    Compte.builder()
                            .numeroCompte("BQ-2026-000105").titulaire("Hind Berrada")
                            .agence("Tanger Malabata")
                            .solde(BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 900000))
                                    .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP))
                            .devise("MAD").dateCreation(LocalDate.of(2025, 1, 30))
                            .type(TypeCompte.PROFESSIONNEL).actif(true).build());

            compteRepository.saveAll(comptes);

            trace.info("=== Initialisation de la base H2 ===");
            compteRepository.findAll().forEach(c -> trace.info("{}", c));
            trace.info("=== Nombre total de comptes : {} ===", compteRepository.count());
        };
    }
}
