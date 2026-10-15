package de.buildingscanner.detection;

import de.buildingscanner.domain.BoundingBox;
import org.springframework.stereotype.Component;

import java.util.List;

//Until real ML / AI detection is implemented, e.g. OpenAI or ONNX
@Component
public class StubDetector implements BuildingDetector {

    @Override
    public List<DetectedBuilding> detect(byte[] image) {
        return List.of(
                new DetectedBuilding(new BoundingBox(40, 60, 120, 90), 0.94),
                new DetectedBuilding(new BoundingBox(220, 50, 80, 80), 0.81),
                new DetectedBuilding(new BoundingBox(150, 200, 60, 40), 0.47));
    }
}
