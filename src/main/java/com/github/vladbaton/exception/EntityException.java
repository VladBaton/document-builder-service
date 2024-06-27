package com.github.vladbaton.exception;

public class EntityException extends RuntimeException {
    private Long id;

    public EntityException() {}


    public EntityException(Long id) {
        this.id = id;
    }

    public EntityException(Long id, String message) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
