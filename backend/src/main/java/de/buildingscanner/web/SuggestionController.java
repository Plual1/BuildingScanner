package de.buildingscanner.web;

import de.buildingscanner.service.SuggestionService;
import de.buildingscanner.web.Dtos.DecisionRequest;
import de.buildingscanner.web.Dtos.SuggestionDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
class SuggestionController {

    private final SuggestionService service;

    SuggestionController(SuggestionService service) {
        this.service = service;
    }

    @PostMapping(path = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    List<SuggestionDto> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        return service.analyzeImage(file.getBytes()).stream().map(SuggestionDto::from).toList();
    }

    @PostMapping("/suggestions/{id}/decision")
    SuggestionDto decide(@PathVariable Long id, @Valid @RequestBody DecisionRequest request) {
        return SuggestionDto.from(service.decide(id, request.accepted()));
    }
}
