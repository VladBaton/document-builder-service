package com.github.vladbaton.exception;

public class FailedToUpdateFileException extends FileException{
    public FailedToUpdateFileException() {}

    public FailedToUpdateFileException(String filename) {
        super(filename);
    }
    public FailedToUpdateFileException(String filename, String message) {
        super(filename, message);
    }
}
