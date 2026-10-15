# Datenmodell und Architektur

## Datenmodell

```
Suggestion
  id
  sessionKey   UUID
  box          (x, y, width, height)
  confidence
  status       OPEN | ACCEPTED | REJECTED
  decidedBy, decidedAt
```

## Flow

```
Angular > Spring Boot (BuildingDetectorInterface, AI) > PostgreSQL
```

## API

- `POST /api/images` (multipart, `file`, max 10MB) > `201` + `SuggestionDto[]`
- `POST /api/suggestions/{id}/decision` (`{ "accepted": true }`) > `200` + `SuggestionDto`
- Fehler als RFC 9457 Problem Details: `400` ungültig, `404` unbekannt, `409` bereits entschieden, `413` zu groß, `415` kein Bild
- Mapping von Entities > DTOs in `web/Dtos.java`
