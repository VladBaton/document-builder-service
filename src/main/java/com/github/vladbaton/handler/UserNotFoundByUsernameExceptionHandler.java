package com.github.vladbaton.handler;

import com.github.vladbaton.exception.UserNotFoundByUsernameException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class UserNotFoundByUsernameExceptionHandler implements ExceptionMapper<UserNotFoundByUsernameException> {
    @Override
    public Response toResponse(final UserNotFoundByUsernameException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(String.format("Пользователь с именем %s не найден", exception.getUsername()))
                .build();
    }
}
