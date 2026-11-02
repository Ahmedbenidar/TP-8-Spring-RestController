# TP 8 - API REST avec Spring RestController

## 1. Démarrage de l'application

Le journal réel du lancement : Spring Boot 3.3.5 sur Java 21, Tomcat sur le port 8082,
création de la table COMPTE par Hibernate, puis le CommandLineRunner qui insère les
cinq comptes et affiche le total.

![Démarrage de l'application](docs/screenshots/app-demarrage.png)

---

## 2. Le contrôleur dans Swagger

La documentation est générée par SpringDoc : les huit opérations, leurs paramètres et
leurs codes de retour sont décrits à partir des annotations du contrôleur.

![Liste des endpoints dans Swagger UI](docs/screenshots/swagger-ui-endpoints.png)

Un essai réel depuis l'interface, filtré sur les comptes d'épargne, renvoie bien un code
200 avec le corps XML attendu.

![Essai réel depuis Swagger UI](docs/screenshots/swagger-try-it-out.png)

---

## 3. Lecture des comptes en JSON et en XML

La même URL /banque/comptes renvoie du JSON ou du XML selon l'en-tête Accept. La
liste est enveloppée dans un élément racine comptes, chaque compte devenant un élément
compte. La lecture d'un compte unique suit la même logique, et un identifiant inconnu
déclenche une réponse 404 avec un corps d'erreur explicite.

![Tests réels de lecture JSON et XML](docs/screenshots/tests-json-et-xml.png)

Dans un navigateur, l'appel sans en-tête Accept particulier affiche l'arbre XML.

![Rendu XML dans le navigateur](docs/screenshots/navigateur-xml.png)

---

## 4. Filtres, recherche et statistiques

La liste accepte quatre filtres optionnels : le type de compte, un intervalle de solde,
un fragment du nom du titulaire et un drapeau pour ne garder que les comptes actifs. Un
endpoint dédié renvoie les indicateurs agrégés : nombre de comptes par catégorie, solde
total, solde moyen et solde maximum.

![Tests réels des filtres et des statistiques](docs/screenshots/tests-filtres.png)

---

## 5. Écriture : création, remplacement, statut, suppression

La création accepte un corps JSON ou XML et répond un code 201 accompagné de l'en-tête
Location pointant vers la ressource créée. Le remplacement complet utilise PUT, le
changement de statut un PATCH, et la suppression répond un 204 sans corps. Un corps
invalide, ici un solde négatif, est intercepté par la validation et renvoyé en 400 avec
le message du champ fautif.

![Tests réels des écritures](docs/screenshots/tests-ecritures.png)

---

## 6. Contrat OpenAPI exposé

Le schéma complet est servi sur /v3/api-docs : il reprend le titre, la description, les
informations de contact et les cinq chemins de l'API.

![Schéma OpenAPI servi par SpringDoc](docs/screenshots/tests-openapi-json.png)

---

## 7. Vérification côté base

La console H2 est activée sur /h2-console. Connectée à la même base mémoire que
l'application, elle permet de constater l'effet réel des appels précédents : les comptes
créés sont présents, celui supprimé a bien disparu et le compte réactivé porte la valeur
attendue.

![Connexion à la console H2](docs/screenshots/console-h2-connexion.png)

![Contenu réel de la table COMPTE](docs/screenshots/console-h2.png)

