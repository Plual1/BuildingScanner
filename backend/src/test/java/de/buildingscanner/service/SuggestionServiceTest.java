package de.buildingscanner.service;

import de.buildingscanner.detection.StubDetector;
import de.buildingscanner.domain.BoundingBox;
import de.buildingscanner.domain.DecisionStatus;
import de.buildingscanner.domain.Suggestion;
import de.buildingscanner.domain.SuggestionAlreadyDecidedException;
import de.buildingscanner.repository.SuggestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SuggestionServiceTest {

    private static final Instant NOW = Instant.parse("2026-10-11T10:00:00Z");
    private static final byte[] PNG_HEADER = {(byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A, 0};

    private final SuggestionRepository suggestions = mock(SuggestionRepository.class);
    private SuggestionService service;

    @BeforeEach
    void setUp() {
        service = new SuggestionService(suggestions, new StubDetector(), () -> "tester",
                Clock.fixed(NOW, ZoneOffset.UTC));
    }

    @Test
    void suggestionCanOnlyBeDecidedOnce() {
        Suggestion suggestion = new Suggestion(UUID.randomUUID(), new BoundingBox(0, 0, 10, 10), 0.9);
        when(suggestions.findById(1L)).thenReturn(Optional.of(suggestion));

        service.decide(1L, true);

        assertThat(suggestion.getStatus()).isEqualTo(DecisionStatus.ACCEPTED);
        assertThat(suggestion.getDecidedBy()).isEqualTo("tester");
        assertThat(suggestion.getDecidedAt()).isEqualTo(NOW);
        assertThatThrownBy(() -> service.decide(1L, false))
                .isInstanceOf(SuggestionAlreadyDecidedException.class);
        assertThat(suggestion.getStatus()).isEqualTo(DecisionStatus.ACCEPTED);
    }

    @SuppressWarnings("null")
    @Test
    void analyzeImageCreatesOneOpenSuggestionPerDetectedBuilding() {
        when(suggestions.saveAll(anyList())).thenAnswer(call -> call.getArgument(0));

        List<Suggestion> created = service.analyzeImage(PNG_HEADER);

        assertThat(created).hasSize(3).allMatch(s -> s.getStatus() == DecisionStatus.OPEN);
        assertThat(created).extracting(Suggestion::getSessionKey)
                .doesNotContainNull()
                .containsOnly(created.get(0).getSessionKey());
    }

    @Test
    void analyzeImageRejectsNonImageBytes() {
        byte[] notAnImage = "GIF89a".getBytes();

        assertThatThrownBy(() -> service.analyzeImage(notAnImage))
                .isInstanceOf(UnsupportedImageException.class);
        verify(suggestions, never()).saveAll(anyList());
    }
}
