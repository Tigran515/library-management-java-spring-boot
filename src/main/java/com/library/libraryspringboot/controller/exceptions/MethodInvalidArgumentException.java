package com.library.libraryspringboot.controller.exceptions;

public class MethodInvalidArgumentException extends RuntimeException {

    public MethodInvalidArgumentException(String message) {
        super(message);
    }
}