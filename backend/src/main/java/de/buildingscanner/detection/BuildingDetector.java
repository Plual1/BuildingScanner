package de.buildingscanner.detection;

import java.util.List;

public interface BuildingDetector {

    List<DetectedBuilding> detect(byte[] image);
}
