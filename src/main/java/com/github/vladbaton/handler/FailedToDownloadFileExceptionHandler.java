package com.github.vladbaton.handler;

import com.github.vladbaton.exception.FailedToDownloadFileException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class FailedToDownloadFileExceptionHandler implements ExceptionMapper<FailedToDownloadFileException> {
    @Override
    public Response toResponse(FailedToDownloadFileException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(String.format("Не удалось скачать файл %s", e.getFilename()))
                .build();
    }
}
