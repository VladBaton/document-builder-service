package com.github.vladbaton.handler;

import com.github.vladbaton.exception.DocumentBuildingError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class DocumentBuildingErrorHandler implements ExceptionMapper<DocumentBuildingError> {
    @Override
    public Response toResponse(final DocumentBuildingError error) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Не удалось выполнить построение pdf файла для файла " +  error.getFilename() +
                        ((error.getMessage() == null)? "" : "Причина: " + error.getMessage()))
                .build();
    }
}
