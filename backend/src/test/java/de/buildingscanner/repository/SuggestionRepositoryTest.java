package de.buildingscanner.repository;

import de.buildingscanner.domain.BoundingBox;
import de.buildingscanner.domain.Suggestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository level: real Postgres, real Flyway migration.
 * Skipped when no Docker is available
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
class SuggestionRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private SuggestionRepository suggestions;

    @SuppressWarnings("null")
    @Test
    void findsSuggestionsOfOneSessionInCreationOrder() {
        UUID mine = UUID.randomUUID();
        UUID other = UUID.randomUUID();
        Suggestion first = suggestions.save(new Suggestion(mine, new BoundingBox(0, 0, 1, 1), 0.5));
        suggestions.save(new Suggestion(other, new BoundingBox(0, 0, 1, 1), 0.5));
        Suggestion second = suggestions.save(new Suggestion(mine, new BoundingBox(5, 5, 1, 1), 0.7));

        List<Suggestion> found = suggestions.findBySessionKeyOrderById(mine);

        assertThat(found).extracting(Suggestion::getId).containsExactly(first.getId(), second.getId());
    }
}
