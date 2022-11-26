package one.digitalinnovation.contact.domain.rest.controller;

import one.digitalinnovation.contact.exceptions.ApiErrors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus
    public ApiErrors handleNotFoundException(ResponseStatusException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

}
