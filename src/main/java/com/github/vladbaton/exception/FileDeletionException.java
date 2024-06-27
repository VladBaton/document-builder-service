package com.github.vladbaton.exception;

public class FileDeletionException extends FileException {

    public FileDeletionException() {
    }

    public FileDeletionException(String fileReference) {
        super(fileReference);
    }
}
