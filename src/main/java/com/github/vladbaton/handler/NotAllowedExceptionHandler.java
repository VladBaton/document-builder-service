package com.github.vladbaton.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class NotAllowedExceptionHandler implements ExceptionMapper<NotAllowedException> {
    @Override
    public Response toResponse(NotAllowedException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .build();
    }
}
