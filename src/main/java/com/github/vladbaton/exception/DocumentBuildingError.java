package com.github.vladbaton.exception;

public class DocumentBuildingError extends FileException {
    public DocumentBuildingError(String filename) {
        super(filename);
    }

    public DocumentBuildingError(String message, String filename) {
        super(filename, message);
    }
}
