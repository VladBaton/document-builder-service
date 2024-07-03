package com.github.vladbaton.exception;

public class BadRequest extends RuntimeException {
    public BadRequest() {

    }

    public BadRequest(String message) {
        super(message);
    }
}
