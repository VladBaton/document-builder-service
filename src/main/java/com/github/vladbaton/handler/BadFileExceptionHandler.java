package com.github.vladbaton.handler;

import com.github.vladbaton.exception.BadFileException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BadFileExceptionHandler implements ExceptionMapper<BadFileException> {
    @Override
    public Response toResponse(final BadFileException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(String.format(
                        "Дружок-пирожок, тобой был передан плохой файл. Имя файла: %s, информация об ошибке: %s",
                        e.getFilename(), e.getMessage())
                ).build();
    }
}
