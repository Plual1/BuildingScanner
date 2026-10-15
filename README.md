## How to run:

- Install Docker (Desktop)
- run `docker compose up --build`


## Interact

- UI: http://localhost:4200
- API: http://localhost:8080/api
- Datenbank: localhost:5432 (Zugangsdaten siehe `.env.example`)

## Tests

```powershell
cd backend;  .\mvnw.cmd verify
cd frontend; npm test -- --watch=false
```

## Mein Arbeitsprozess

- Prototyp Idee entwicklen: Architektur, skalierbar, einfach ([docs/architektur.md](docs/architektur.md))
- Idee als Anforderungen strukturieren ([docs/anforderungen.md](docs/anforderungen.md))
- Teststrategie entwickeln ([docs/teststrategie.md](docs/teststrategie.md))
- Project erstellen (Frontend, Backend, Datenbank)
- Eine Aktion E2E implementieren
- Tests schreiben
- Erweiterte Features spezifizieren 
