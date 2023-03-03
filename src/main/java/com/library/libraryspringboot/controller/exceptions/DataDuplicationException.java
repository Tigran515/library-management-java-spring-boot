package com.library.libraryspringboot.controller.exceptions;

public class DataDuplicationException extends RuntimeException {
    public DataDuplicationException(String message) {
        super(message);
    }
}
