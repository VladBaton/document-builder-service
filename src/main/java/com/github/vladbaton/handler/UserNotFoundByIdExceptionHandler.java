package com.github.vladbaton.handler;

import com.github.vladbaton.exception.UserNotFoundByIdException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class UserNotFoundByIdExceptionHandler implements ExceptionMapper<UserNotFoundByIdException> {
    @Override
    public Response toResponse(final UserNotFoundByIdException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(String.format("Пользователь с id %d не найден", exception.getId()))
                .build();
    }
}
