package com.github.vladbaton.handler;

import com.github.vladbaton.exception.WrongAuthorizationHeaderException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class WrongAuthorizationHeaderExceptionHandler implements ExceptionMapper<WrongAuthorizationHeaderException> {
    @Override
    public Response toResponse(WrongAuthorizationHeaderException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Неверные логин или пароль")
                .build();
    }
}
