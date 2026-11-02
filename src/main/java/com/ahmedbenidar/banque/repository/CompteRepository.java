package com.ahmedbenidar.banque.repository;

import com.ahmedbenidar.banque.domain.Compte;
import com.ahmedbenidar.banque.domain.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    Optional<Compte> findByNumeroCompte(String numeroCompte);

    boolean existsByNumeroCompte(String numeroCompte);

    List<Compte> findByType(TypeCompte type);

    List<Compte> findByActifTrueOrderBySoldeDesc();

    List<Compte> findBySoldeBetweenOrderBySoldeAsc(BigDecimal min, BigDecimal max);

    List<Compte> findByTitulaireContainingIgnoreCase(String fragment);

    @Query("select coalesce(sum(c.solde), 0) from Compte c where c.type = :type")
    BigDecimal totalSoldeParType(@Param("type") TypeCompte type);

    long countByType(TypeCompte type);
}
