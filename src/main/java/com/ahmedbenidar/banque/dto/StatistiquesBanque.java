package com.ahmedbenidar.banque.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;

/** Indicateurs agrégés renvoyés par {@code GET /banque/statistiques}. */
@JacksonXmlRootElement(localName = "statistiques")
public record StatistiquesBanque(
        long nombreComptes,
        long comptesCourants,
        long comptesEpargne,
        long comptesProfessionnels,
        BigDecimal soldeTotal,
        BigDecimal soldeMoyen,
        BigDecimal soldeMax,
        BigDecimal totalCourants,
        BigDecimal totalEpargne,
        BigDecimal totalProfessionnels
) {
}
