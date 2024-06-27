package com.github.vladbaton.exception;

public class UserNotFoundByIdException extends EntityException {
    public UserNotFoundByIdException(Long id) {
        super(id);
    }

    public Long getId() {
        return super.getId();
    }
}
