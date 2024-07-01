package com.github.vladbaton.handler;

import com.github.vladbaton.exception.AdminDeletionException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class AdminDeletionExceptionHandler implements ExceptionMapper<AdminDeletionException> {
    @Override
    public Response toResponse(AdminDeletionException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(String.format("Пользователь с id %d является администратором. " +
                        "Нельзя удалить администратора", e.getId()))
                .build();
    }
}
