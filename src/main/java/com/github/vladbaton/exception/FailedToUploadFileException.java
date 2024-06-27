package com.github.vladbaton.exception;

public class FailedToUploadFileException extends FileException {
    public FailedToUploadFileException() {}

    public FailedToUploadFileException(String filename) {
        super(filename);
    }

    public FailedToUploadFileException(String filename, String message) {
        super(filename, message);
    }
}
