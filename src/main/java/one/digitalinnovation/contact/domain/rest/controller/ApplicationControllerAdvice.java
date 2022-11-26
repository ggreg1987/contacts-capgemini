package one.digitalinnovation.contact.domain.rest.controller;

import one.digitalinnovation.contact.exceptions.ApiErrors;
import one.digitalinnovation.contact.exceptions.BadRequestException;
import one.digitalinnovation.contact.exceptions.InvalidDateException;
import one.digitalinnovation.contact.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleBadRequestException(BadRequestException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handleNotFoundException(NotFoundException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleInvalidDateException(InvalidDateException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }
}
