package de.buildingscanner.repository;

import de.buildingscanner.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    List<Suggestion> findBySessionKeyOrderById(UUID sessionKey);
}
