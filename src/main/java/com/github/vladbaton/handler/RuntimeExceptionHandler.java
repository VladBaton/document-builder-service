package com.github.vladbaton.handler;

import com.github.vladbaton.exception.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@ApplicationScoped
@Provider
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof AdminDeletionException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.format("Пользователь с id %d является администратором. " +
                            "Нельзя удалить администратора", ((AdminDeletionException) e).getId()))
                    .build();
        } else if(e instanceof FailedToDownloadFileException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("Не удалось скачать файл %s", ((FailedToDownloadFileException) e).getFilename()))
                    .build();
        } else if (e instanceof FailedToUpdateFileException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("Не обновить файл %s", ((FailedToUpdateFileException) e).getFilename()))
                    .build();
        } else if (e instanceof FailedToUploadFileException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("Не удалось загрузить файл %s", ((FailedToUploadFileException) e).getFilename()))
                    .build();
        } else if (e instanceof FileDeletionException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(String.format("Не удалось удалить файл %s", ((FileDeletionException) e).getFilename()))
                    .build();
        } else if (e instanceof FileNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Файл %s не найден", ((FileNotFoundException) e).getFilename()))
                    .build();
        } else if (e instanceof UserNotFoundByIdException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Пользователь с id %d не найден", ((UserNotFoundByIdException) e).getId()))
                    .build();
        } else if (e instanceof  UserNotFoundByUsernameException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Пользователь с именем %s не найден", ((UserNotFoundByUsernameException) e).getUsername()))
                    .build();
        } else if (e instanceof  WrongAuthorizationHeaderException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Неверные логин или пароль")
                    .build();
        } else if (e instanceof ConstraintViolationException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Нарушено одно или несколько правил валидации")
                    .build();
        } else if (e instanceof DocumentBuildingError) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Не удалось выполнить построение pdf файла для файла " + ((DocumentBuildingError) e).getFilename() +
                            ((e.getMessage() == null)? "" : "Причина: " + e.getMessage()))
                    .build();
        } else if (e instanceof NotAllowedException) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Неизвестная ошибка сервера " + e.getClass().getSimpleName())
                    .build();
        }
    }
}
