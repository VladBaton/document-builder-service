package com.github.vladbaton.handler;

import com.github.vladbaton.exception.FileDeletionException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class FileDeletionExceptionHandler implements ExceptionMapper<FileDeletionException> {
    @Override
    public Response toResponse(FileDeletionException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(String.format("Не удалось удалить файл %s", e.getFilename()))
                .build();
    }
}
