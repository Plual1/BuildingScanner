# Szenario "BuildingScanner"

Idee: Skalierbare App, um einem Gutachter Gebäudeerfassung zu ermöglichen. 

Arbeitsreihenfolge: 
1) Prototyp mit Hauptfunktionen
2) Erweitern ( siehe unten)

## Prototyp

Funktionen: 

- (A)Bild Hochladen
- (B) Prüfen
- (B) Simulierte Gebäudeereknnung
- (B) Vorschläge speichern und zurück ans Frontend übergeben
- (A) "Human-In-The-Loop" accept/reject
- (B) Update der Vorschläge


## Erweiterte Funktionen ("next steps")

### Frontend

- Rollen-/Benutzerverwaltung UI
- Mehrere Seiten: Login, Propose, Review, Log
- Mobile App/PWA
- Frühere Erfassungen editieren

### Backend
- Mehrere Benutzer, mehrere Rollen ( Gutachter, Auftraggeber, ...)
- Echtes ML-Modell oder AI
- API erweitern: Sessions speichern und editieren, login, ..

## Generell

- Wie viele Begehungen / Bilder pro Monat? (bestimmt Hosting, Kosten, Modellwahl)
- Ist eine erkannte Gebäudeanzahl rechtlich bindend oder nur ein Vorschlag?
- Wer haftet bei einer Fehlerkennung, die der Gutachter übersieht?
- Gibt es ein bestehendes Login-System (OIDC, AD)?
- In welchem Format wird das Ergebnis weiterverarbeitet (PDF, GIS, ...)?
- Datenspeicherung im (EU) Ausland?