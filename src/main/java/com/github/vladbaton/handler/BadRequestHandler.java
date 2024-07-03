package com.github.vladbaton.handler;

import com.github.vladbaton.exception.BadRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BadRequestHandler implements ExceptionMapper<BadRequest> {
    @Override
    public Response toResponse(final BadRequest badRequest) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
