package de.buildingscanner.domain;

public class SuggestionAlreadyDecidedException extends RuntimeException {

    public SuggestionAlreadyDecidedException(Long suggestionId) {
        super("Suggestion " + suggestionId + " has already been decided");
    }
}
