package de.buildingscanner.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "suggestion")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //placeholder for enabling multiple sessions
    @Column(name = "session_key", nullable = false, updatable = false)
    private UUID sessionKey;

    @Embedded
    private BoundingBox box;

    @Column(nullable = false)
    private double confidence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DecisionStatus status = DecisionStatus.OPEN;

    @Column(name = "decided_by", length = 100)
    private String decidedBy;

    @Column(name = "decided_at")
    private Instant decidedAt;

    protected Suggestion() {
    }

    public Suggestion(UUID sessionKey, BoundingBox box, double confidence) {
        this.sessionKey = sessionKey;
        this.box = box;
        this.confidence = confidence;
    }

    public void decide(boolean accepted, String user, Instant now) {
        if (status != DecisionStatus.OPEN) {
            throw new SuggestionAlreadyDecidedException(id);
        }
        this.status = accepted ? DecisionStatus.ACCEPTED : DecisionStatus.REJECTED;
        this.decidedBy = user;
        this.decidedAt = now;
    }

    public Long getId() {
        return id;
    }

    public UUID getSessionKey() {
        return sessionKey;
    }

    public BoundingBox getBox() {
        return box;
    }

    public double getConfidence() {
        return confidence;
    }

    public DecisionStatus getStatus() {
        return status;
    }

    public String getDecidedBy() {
        return decidedBy;
    }

    public Instant getDecidedAt() {
        return decidedAt;
    }
}
