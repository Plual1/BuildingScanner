package de.buildingscanner.web;

import de.buildingscanner.domain.SuggestionAlreadyDecidedException;
import de.buildingscanner.service.NotFoundException;
import de.buildingscanner.service.UnsupportedImageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Maps domain failures to RFC 9457 problem responses. Framework errors (400, 413) via spring.mvc.problemdetails. */
@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ProblemDetail notFound(NotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(SuggestionAlreadyDecidedException.class)
    ProblemDetail alreadyDecided(SuggestionAlreadyDecidedException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(UnsupportedImageException.class)
    ProblemDetail unsupportedImage(UnsupportedImageException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }
}
