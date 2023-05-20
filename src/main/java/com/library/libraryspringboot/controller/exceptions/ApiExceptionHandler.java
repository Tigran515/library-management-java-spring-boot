package com.library.libraryspringboot.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage noSuchElementException(HttpServletRequest request, NoSuchElementException ex) {
//        LOGGER.error("Handling NoSuchElementException: [{} for request {} {}]", ex.getMessage(), request.getMethod(), request.getRequestURI());
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "Resource Not Found");
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage methodInvalidArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
//        LOGGER.error("Handling IllegalArgumentException: [{} for request {} {}]", ex.getMessage(), request.getMethod(), request.getRequestURI());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                "User input arguments are not valid");
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    //DuplicateElementException is a custom exception, DuplicateKeyException is passed instead
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage duplicateKeyException(HttpServletRequest request, DuplicateKeyException ex) {
//        LOGGER.error("Handling IllegalArgumentException: [{} for request {} {}]", ex.getMessage(), request.getMethod(), request.getRequestURI());
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                "Data already exists"
        );
    }
}
