# ============================================================================
#  TP 8 - Tests reels des endpoints du CompteController (@RestController)
#  Chaque appel est affiche, puis son resultat, puis le code HTTP mesure
#  dans le meme appel (curl -w "\n[HTTP %{http_code}]").
# ============================================================================

$base = "http://localhost:8082"
$ici = Split-Path -Parent $PSScriptRoot

function Titre($texte) {
    Write-Host ""
    Write-Host ("=" * 78) -ForegroundColor Cyan
    Write-Host $texte -ForegroundColor Cyan
    Write-Host ("=" * 78) -ForegroundColor Cyan
}

function Executer($commande) {
    Write-Host ""
    Write-Host "> $commande" -ForegroundColor Yellow
    Write-Host ""
}

Titre "SECTION 1 - GET /banque/comptes (JSON)"
Executer "curl.exe -s -H `"Accept: application/json`" $base/banque/comptes -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 2 - GET /banque/comptes (XML)"
Executer "curl.exe -s -H `"Accept: application/xml`" $base/banque/comptes -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/xml" "$base/banque/comptes" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 3 - GET /banque/comptes/1 (JSON)"
Executer "curl.exe -s -H `"Accept: application/json`" $base/banque/comptes/1 -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes/1" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 4 - GET /banque/comptes/1 (XML)"
Executer "curl.exe -s -H `"Accept: application/xml`" $base/banque/comptes/1 -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/xml" "$base/banque/comptes/1" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 5 - GET /banque/comptes/9999 -> 404 JSON"
Executer "curl.exe -s -H `"Accept: application/json`" $base/banque/comptes/9999 -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes/9999" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 6 - GET /banque/comptes?type=EPARGNE"
Executer "curl.exe -s -H `"Accept: application/json`" `"$base/banque/comptes?type=EPARGNE`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes?type=EPARGNE" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 7 - GET /banque/comptes?soldeMin=10000&soldeMax=20000"
Executer "curl.exe -s -H `"Accept: application/json`" `"$base/banque/comptes?soldeMin=10000&soldeMax=20000`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes?soldeMin=10000&soldeMax=20000" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 8 - GET /banque/comptes?titulaire=ouazzani"
Executer "curl.exe -s -H `"Accept: application/json`" `"$base/banque/comptes?titulaire=ouazzani`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes?titulaire=ouazzani" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 9 - GET /banque/comptes?actifsSeulement=true"
Executer "curl.exe -s -H `"Accept: application/json`" `"$base/banque/comptes?actifsSeulement=true`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes?actifsSeulement=true" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 10 - GET /banque/comptes/reference/BQ-2026-000102"
Executer "curl.exe -s -H `"Accept: application/json`" $base/banque/comptes/reference/BQ-2026-000102 -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes/reference/BQ-2026-000102" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 11 - GET /banque/comptes/statistiques"
Executer "curl.exe -s -H `"Accept: application/json`" $base/banque/comptes/statistiques -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes/statistiques" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 12 - POST /banque/comptes (corps JSON) -> 201 + Location"
Executer "curl.exe -s -D - -X POST $base/banque/comptes -H `"Content-Type: application/json`" -H `"Accept: application/json`" -d `"@docs/payloads/compte-nouveau.json`""
curl.exe -s -D - -X POST "$base/banque/comptes" -H "Content-Type: application/json" -H "Accept: application/json" -d "@$ici/payloads/compte-nouveau.json"
Write-Host ""

Titre "SECTION 13 - POST /banque/comptes (corps XML) -> 201 + Location"
Executer "curl.exe -s -D - -X POST $base/banque/comptes -H `"Content-Type: application/xml`" -H `"Accept: application/xml`" -d `"@docs/payloads/compte-nouveau.xml`""
curl.exe -s -D - -X POST "$base/banque/comptes" -H "Content-Type: application/xml" -H "Accept: application/xml" -d "@$ici/payloads/compte-nouveau.xml"
Write-Host ""

Titre "SECTION 14 - POST invalide (solde negatif) -> 400"
Executer "curl.exe -s -X POST $base/banque/comptes -H `"Content-Type: application/json`" -d `"@docs/payloads/compte-invalide.json`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -X POST "$base/banque/comptes" -H "Content-Type: application/json" -H "Accept: application/json" -d "@$ici/payloads/compte-invalide.json" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 15 - PUT /banque/comptes/1 (corps JSON)"
Executer "curl.exe -s -X PUT $base/banque/comptes/1 -H `"Content-Type: application/json`" -H `"Accept: application/json`" -d `"@docs/payloads/compte-modifie.json`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -X PUT "$base/banque/comptes/1" -H "Content-Type: application/json" -H "Accept: application/json" -d "@$ici/payloads/compte-modifie.json" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 16 - PATCH /banque/comptes/4/statut?actif=true"
Executer "curl.exe -s -X PATCH `"$base/banque/comptes/4/statut?actif=true`" -H `"Accept: application/json`" -w `"\n[HTTP %{http_code}]`""
curl.exe -s -X PATCH "$base/banque/comptes/4/statut?actif=true" -H "Accept: application/json" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 17 - DELETE /banque/comptes/6 -> 204"
Executer "curl.exe -s -X DELETE $base/banque/comptes/6 -w `"\n[HTTP %{http_code}]`""
curl.exe -s -X DELETE "$base/banque/comptes/6" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 18 - Verification finale : statistiques apres modifications"
Executer "curl.exe -s -H `"Accept: application/json`" $base/banque/comptes/statistiques -w `"\n[HTTP %{http_code}]`""
curl.exe -s -H "Accept: application/json" "$base/banque/comptes/statistiques" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "SECTION 19 - Documentation OpenAPI generee par springdoc"
Executer "curl.exe -s $base/v3/api-docs -w `"\n[HTTP %{http_code}]`""
curl.exe -s "$base/v3/api-docs" -o "$ici/openapi.json" -w "`n[HTTP %{http_code}]"
Write-Host ""

Titre "FIN DES TESTS"
