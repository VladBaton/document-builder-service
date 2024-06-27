package com.github.vladbaton.exception;

public class FailedToDownloadFileException extends FileException {

    public FailedToDownloadFileException(String filename) {
        super(filename);
    }
}
