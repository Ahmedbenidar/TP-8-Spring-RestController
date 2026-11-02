package com.ahmedbenidar.banque.web;

import com.ahmedbenidar.banque.domain.Compte;
import com.ahmedbenidar.banque.domain.TypeCompte;
import com.ahmedbenidar.banque.dto.Comptes;
import com.ahmedbenidar.banque.dto.StatistiquesBanque;
import com.ahmedbenidar.banque.service.CompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/banque", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Comptes bancaires", description = "CRUD complet, filtres et statistiques - JSON et XML")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping("/comptes")
    @Operation(summary = "Lister les comptes",
            description = "Filtres optionnels : type, solde min/max, titulaire, actifs seulement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste renvoyee en JSON ou en XML"),
            @ApiResponse(responseCode = "400", description = "Parametre de filtre invalide")
    })
    public Comptes listerComptes(
            @Parameter(description = "COURANT, EPARGNE ou PROFESSIONNEL")
            @RequestParam(required = false) TypeCompte type,
            @Parameter(description = "Solde minimum inclus")
            @RequestParam(required = false) BigDecimal soldeMin,
            @Parameter(description = "Solde maximum inclus")
            @RequestParam(required = false) BigDecimal soldeMax,
            @Parameter(description = "Fragment du nom du titulaire")
            @RequestParam(required = false) String titulaire,
            @Parameter(description = "Ne garder que les comptes actifs")
            @RequestParam(required = false, defaultValue = "false") boolean actifsSeulement) {

        List<Compte> resultat;
        if (titulaire != null && !titulaire.isBlank()) {
            resultat = compteService.rechercherParTitulaire(titulaire);
        } else if (soldeMin != null || soldeMax != null) {
            resultat = compteService.rechercherParIntervalleDeSolde(soldeMin, soldeMax);
        } else if (actifsSeulement) {
            resultat = compteService.listerActifsTriesParSolde();
        } else if (type != null) {
            resultat = compteService.listerParType(type);
        } else {
            resultat = compteService.listerComptes();
        }
        return Comptes.de(resultat);
    }

    @GetMapping("/comptes/{id}")
    @Operation(summary = "Consulter un compte par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compte trouve"),
            @ApiResponse(responseCode = "404", description = "Identifiant inconnu")
    })
    public ResponseEntity<Compte> consulterCompte(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.trouverParId(id));
    }

    @GetMapping("/comptes/reference/{numero}")
    @Operation(summary = "Consulter un compte par sa reference metier")
    public ResponseEntity<Compte> consulterParReference(@PathVariable String numero) {
        return ResponseEntity.ok(compteService.trouverParNumero(numero));
    }

    @GetMapping("/comptes/statistiques")
    @Operation(summary = "Indicateurs agreges sur l'ensemble des comptes")
    public ResponseEntity<StatistiquesBanque> statistiques() {
        return ResponseEntity.ok(compteService.statistiques());
    }

    @PostMapping(value = "/comptes",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Creer un compte",
            description = "Corps en JSON ou en XML. Reponse 201 avec l'en-tete Location.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Compte cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public ResponseEntity<Compte> creerCompte(@Valid @RequestBody Compte compte) {
        Compte cree = compteService.creer(compte);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.getId())
                .toUri();
        return ResponseEntity.created(location).body(cree);
    }

    @PutMapping(value = "/comptes/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Remplacer un compte existant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compte mis a jour"),
            @ApiResponse(responseCode = "404", description = "Identifiant inconnu")
    })
    public ResponseEntity<Compte> remplacerCompte(@PathVariable Long id,
                                                  @Valid @RequestBody Compte compte) {
        return ResponseEntity.ok(compteService.remplacer(id, compte));
    }

    @PatchMapping("/comptes/{id}/statut")
    @Operation(summary = "Activer ou desactiver un compte")
    public ResponseEntity<Compte> changerStatut(@PathVariable Long id,
                                                @RequestParam boolean actif) {
        return ResponseEntity.ok(compteService.changerStatut(id, actif));
    }

    @DeleteMapping("/comptes/{id}")
    @Operation(summary = "Supprimer un compte")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Compte supprime"),
            @ApiResponse(responseCode = "404", description = "Identifiant inconnu")
    })
    public ResponseEntity<Void> supprimerCompte(@PathVariable Long id) {
        compteService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
