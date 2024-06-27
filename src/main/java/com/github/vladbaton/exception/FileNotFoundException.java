package com.github.vladbaton.exception;

public class FileNotFoundException extends RuntimeException {
    String filename;
    String username;

    public  FileNotFoundException(String filename) {
        super(filename);
    }

    public FileNotFoundException(String filename, String username) {
        this.filename = filename;
        this.username = username;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
