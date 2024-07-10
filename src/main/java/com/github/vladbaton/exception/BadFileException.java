package com.github.vladbaton.exception;

public class BadFileException extends FileException {
    public BadFileException(String message) {
        super(message);
    }

    public BadFileException(String message, String filename) {
        super(message, filename);
    }
}
