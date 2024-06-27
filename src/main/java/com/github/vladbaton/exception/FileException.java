package com.github.vladbaton.exception;

public class FileException extends RuntimeException {
    private String filename;

    public FileException() {}

    public FileException(String filename) {
        this.filename = filename.substring(filename.lastIndexOf("\\") + 1);
    }

    public FileException(String message, String filename) {
        super(message);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
