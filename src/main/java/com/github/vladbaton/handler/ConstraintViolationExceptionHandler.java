package com.github.vladbaton.handler;

import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.exception.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Provider
@ApplicationScoped
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        String message = e.getMessage();
        if (message == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Ошибка валидации").build();
        }
        int errTextBegin = message.indexOf("ОШИБКА:");
        int errTextEnd = message.indexOf("Call ");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(
                        "Ошибка валидации: " +
                        (errTextBegin > 0 && errTextEnd > 0 ? message.substring(errTextBegin, errTextEnd) : message)
                )
                .build();
    }
}