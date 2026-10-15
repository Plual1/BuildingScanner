package de.buildingscanner.service;

import de.buildingscanner.detection.BuildingDetector;
import de.buildingscanner.domain.Suggestion;
import de.buildingscanner.repository.SuggestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

@Service
public class SuggestionService {

    private final SuggestionRepository suggestionRepo;
    private final BuildingDetector detector;
    private final CurrentUser currentUser;
    private final Clock clock;

    public SuggestionService(SuggestionRepository suggestionRepo, BuildingDetector detector,
                             CurrentUser currentUser, Clock clock) {
        this.suggestionRepo = suggestionRepo;
        this.detector = detector;
        this.currentUser = currentUser;
        this.clock = clock;
    }

    /**
     * The image is only passed through.
     * All suggestions of one upload share a new session key.
     * Deliberately not @Transactional: detection may be slow and must not hold a DB connection.
     * {@code saveAll} brings its own transaction, so the inserts stay all-or-nothing.
     */
    public List<Suggestion> analyzeImage(byte[] image) {
        ImageFormat.requireSupported(image);
        UUID sessionKey = UUID.randomUUID();
        List<Suggestion> created = detector.detect(image).stream()
                .map(building -> new Suggestion(sessionKey, building.box(), building.confidence()))
                .toList();
        return suggestionRepo.saveAll(created);
    }

    /** Transactional so dirty checking writes the decision on commit; there is no explicit save. */
    @Transactional
    public Suggestion decide(Long suggestionId, boolean accepted) {
        Suggestion suggestion = suggestionRepo.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("Suggestion", suggestionId));
        suggestion.decide(accepted, currentUser.name(), clock.instant());
        return suggestion;
    }
}
