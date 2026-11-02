package com.ahmedbenidar.banque.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compte", uniqueConstraints = @UniqueConstraint(columnNames = "numero_compte"))
@JacksonXmlRootElement(localName = "compte")
@JsonPropertyOrder({"id", "numeroCompte", "titulaire", "agence", "solde", "devise", "dateCreation", "type", "actif"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 24)
    @Column(name = "numero_compte", unique = true, length = 24)
    private String numeroCompte;

    @NotBlank
    @Size(max = 80)
    @Column(name = "titulaire", length = 80)
    private String titulaire;

    @Size(max = 40)
    @Column(name = "agence", length = 40)
    private String agence;

    @NotNull
    @PositiveOrZero
    @Column(name = "solde", nullable = false, precision = 15, scale = 2)
    private BigDecimal solde;

    @Size(min = 3, max = 3)
    @Column(name = "devise", length = 3)
    private String devise;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TypeCompte type;

    @Column(name = "actif", nullable = false)
    private boolean actif;
}
