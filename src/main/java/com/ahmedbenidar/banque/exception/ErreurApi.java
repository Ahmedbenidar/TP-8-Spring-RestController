package com.ahmedbenidar.banque.exception;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

/** Corps d'erreur unique, identique en JSON et en XML. */
@JacksonXmlRootElement(localName = "erreur")
public record ErreurApi(
        int code,
        String erreur,
        String message,
        String chemin,
        Instant horodatage
) {
}
