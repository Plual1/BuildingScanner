package de.buildingscanner.detection;

import de.buildingscanner.domain.BoundingBox;

public record DetectedBuilding(BoundingBox box, double confidence) {
}
