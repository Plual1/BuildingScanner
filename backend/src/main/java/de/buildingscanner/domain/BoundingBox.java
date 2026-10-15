package de.buildingscanner.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record BoundingBox(
        @Column(name = "box_x", nullable = false) int x,
        @Column(name = "box_y", nullable = false) int y,
        @Column(name = "box_width", nullable = false) int width,
        @Column(name = "box_height", nullable = false) int height) {
}
