package com.github.vladbaton.resource;


import com.github.vladbaton.exception.UserNotFoundByUsernameException;
import com.github.vladbaton.exception.WrongAuthorizationHeaderException;
import com.github.vladbaton.resource.dto.UserDTO;
import com.github.vladbaton.resource.pojo.UserForUserResponse;
import com.github.vladbaton.service.DocService;
import com.github.vladbaton.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import static com.github.vladbaton.help.AuthChecker.checkBasicAuth;

@ApplicationScoped
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Ресурс работы с данными пользователей", description = "Добавление, удаление изменение юзеров")
public class UserResource {
    @Inject
    UserService userService;

    @Inject
    DocService docService;

    @POST
    @PermitAll
    @Operation(description = "Регистрация юзера")
    @APIResponse(responseCode = "200", description = "Юзер зарегистрирован")
    @APIResponse(responseCode = "400", description = "Пользователь ввёл фигню")
    public Response register(@Valid UserDTO userDTO) throws ConstraintViolationException {
        userService.registerUser(userDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @RolesAllowed("User")
    @Operation(description = "Самоудаление юзера")
    @APIResponse(responseCode = "200", description = "Юзер самоудалён")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве юзера")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    public Response delete(@HeaderParam("Authorization") String authorizationToken)
            throws UserNotFoundByUsernameException, WrongAuthorizationHeaderException {
        userService.deleteUser(checkBasicAuth(authorizationToken)[0]);
        return Response.status(Response.Status.GONE).build();
    }

    @PUT
    @RolesAllowed("User")
    @Operation(description = "Обновление данных о пользователе")
    @APIResponse(responseCode = "200", description = "Данные пользователя обновлены")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве юзера")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "400", description = "Пользователь ввёл фигню")
    public Response update(@HeaderParam("Authorization") String authorizationToken, @Valid UserDTO request)
            throws ConstraintViolationException, WrongAuthorizationHeaderException, UserNotFoundByUsernameException {
        userService.updateUser(checkBasicAuth(authorizationToken)[0], request);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("User")
    @Operation(description = "Прлучение данных о пользователе")
    @APIResponse(responseCode = "200", description = "Данные о пользователе получены")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве юзера")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "400", description = "Пользователь ввёл неверные данные")
    public Response read(@HeaderParam("Authorization") String authorizationToken)
            throws WrongAuthorizationHeaderException {
        return Response.status(Response.Status.OK).entity(new UserForUserResponse(userService.readUser(checkBasicAuth(authorizationToken)[0]))).build();
    }

    @POST
    @Path("/slaves/upload")
    @RolesAllowed("User")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForSlaves(@MultipartForm MultipartFormDataInput inputForm,
                                    @HeaderParam("Authorization") String authorizationToken)
            throws WrongAuthorizationHeaderException {
        docService.searchForSlaves(inputForm);
        return Response.status(Response.Status.CREATED).build();
    }
}
