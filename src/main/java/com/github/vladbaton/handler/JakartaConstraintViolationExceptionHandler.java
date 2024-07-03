package com.github.vladbaton.handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JakartaConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(
                        exception.getConstraintViolations().stream()
                                .map(constraintViolation -> constraintViolation.getMessage() + " " + constraintViolation.getInvalidValue())
                                .toList()
                )
                .build();
    }
}
