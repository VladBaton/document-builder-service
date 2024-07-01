package com.github.vladbaton.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.RollbackException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class RollbackExceptionMapper implements ExceptionMapper<RollbackException> {
    @Override
    public Response toResponse(RollbackException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ВЛАДООООС!").build();
    }
}
