package com.github.vladbaton.exception;

public class AdminDeletionException extends EntityException {
    public AdminDeletionException(Long id) {
        super(id);
    }
}
