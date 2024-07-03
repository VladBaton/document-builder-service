package com.github.vladbaton.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(final ValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Введённые данные неверны! " + exception.getClass())
                .build();
    }
}
