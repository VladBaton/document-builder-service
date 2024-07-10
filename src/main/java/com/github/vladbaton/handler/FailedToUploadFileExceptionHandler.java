package com.github.vladbaton.handler;

import com.github.vladbaton.exception.FailedToUploadFileException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class FailedToUploadFileExceptionHandler implements ExceptionMapper<FailedToUploadFileException> {
    @Override
    public Response toResponse(FailedToUploadFileException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(String.format("Не удалось загрузить файл %s по причине %s", e.getFilename(), e.getMessage()))
                .build();
    }
}
