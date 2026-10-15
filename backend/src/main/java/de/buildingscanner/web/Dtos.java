package de.buildingscanner.web;

import de.buildingscanner.domain.BoundingBox;
import de.buildingscanner.domain.DecisionStatus;
import de.buildingscanner.domain.Suggestion;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

final class Dtos {

    private Dtos() {
    }

    record DecisionRequest(@NotNull Boolean accepted) {
    }

    record BoxDto(int x, int y, int width, int height) {

        static BoxDto from(BoundingBox b) {
            return new BoxDto(b.x(), b.y(), b.width(), b.height());
        }
    }

    enum DecisionStatusDto {
        OPEN, ACCEPTED, REJECTED;

        /** Exhaustive switch: a new domain status fails compilation here instead of leaking into the API. */
        static DecisionStatusDto from(DecisionStatus status) {
            return switch (status) {
                case OPEN -> OPEN;
                case ACCEPTED -> ACCEPTED;
                case REJECTED -> REJECTED;
            };
        }
    }

    record SuggestionDto(Long id, BoxDto box, double confidence, DecisionStatusDto status,
                         String decidedBy, Instant decidedAt) {

        static SuggestionDto from(Suggestion s) {
            return new SuggestionDto(s.getId(), BoxDto.from(s.getBox()), s.getConfidence(),
                    DecisionStatusDto.from(s.getStatus()), s.getDecidedBy(), s.getDecidedAt());
        }
    }
}
