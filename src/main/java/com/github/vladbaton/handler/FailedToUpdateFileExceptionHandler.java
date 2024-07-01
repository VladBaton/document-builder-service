package com.github.vladbaton.handler;

import com.github.vladbaton.exception.FailedToUpdateFileException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class FailedToUpdateFileExceptionHandler implements ExceptionMapper<FailedToUpdateFileException> {
    @Override
    public Response toResponse(FailedToUpdateFileException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(String.format("Не обновить файл %s", e.getFilename()))
                .build();
    }
}
