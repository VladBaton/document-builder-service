package com.github.vladbaton.resource;


import com.github.vladbaton.resource.dto.UserDTO;
import com.github.vladbaton.resource.pojo.RegistrationRequest;
import com.github.vladbaton.resource.pojo.UpdateRequest;
import com.github.vladbaton.resource.pojo.UserForUserResponse;
import com.github.vladbaton.exception.UserNotFoundByUsernameException;
import com.github.vladbaton.exception.WrongAuthorizationHeaderException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.github.vladbaton.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import static com.github.vladbaton.help.AuthChecker.checkBasicAuth;

@ApplicationScoped
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Ресурс работы с данными пользователей", description = "Добавление, удаление изменение юзеров")
public class UserResource {
    @Inject
    UserService userService;

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
    public Response update(@HeaderParam("Authorization") String authorizationToken, UpdateRequest request)
            throws ConstraintViolationException, WrongAuthorizationHeaderException, UserNotFoundByUsernameException {
        userService.updateUser(checkBasicAuth(authorizationToken)[0], request.getUser());
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
}
