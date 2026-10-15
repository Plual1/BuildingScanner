package de.buildingscanner.web;

import de.buildingscanner.domain.BoundingBox;
import de.buildingscanner.domain.Suggestion;
import de.buildingscanner.domain.SuggestionAlreadyDecidedException;
import de.buildingscanner.service.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuggestionController.class)
class SuggestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private SuggestionService service;

    @Test
    void decisionReturnsTheDecidedSuggestion() throws Exception {
        Suggestion suggestion = new Suggestion(UUID.randomUUID(), new BoundingBox(1, 2, 3, 4), 0.9);
        suggestion.decide(true, "tester", Instant.EPOCH);
        when(service.decide(5L, true)).thenReturn(suggestion);

        mvc.perform(post("/api/suggestions/5/decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accepted\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACCEPTED"))
                .andExpect(jsonPath("$.decidedBy").value("tester"))
                .andExpect(jsonPath("$.box.width").value(3));
    }

    @Test
    void secondDecisionIsAConflict() throws Exception {
        when(service.decide(5L, false)).thenThrow(new SuggestionAlreadyDecidedException(5L));

        mvc.perform(post("/api/suggestions/5/decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accepted\": false}"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Suggestion 5 has already been decided"));
    }
}
