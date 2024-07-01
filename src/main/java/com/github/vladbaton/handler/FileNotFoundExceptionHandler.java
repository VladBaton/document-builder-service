package com.github.vladbaton.handler;

import com.github.vladbaton.exception.FileNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class FileNotFoundExceptionHandler implements ExceptionMapper<FileNotFoundException> {
    @Override
    public Response toResponse(final FileNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(String.format("Файл %s не найден", exception.getFilename()))
                .build();
    }
}
