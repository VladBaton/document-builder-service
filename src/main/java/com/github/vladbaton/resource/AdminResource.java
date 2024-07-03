package com.github.vladbaton.resource;

import com.github.vladbaton.resource.pojo.PaginatedUsersForAdminRequest;
import com.github.vladbaton.resource.pojo.UserForAdminResponse;
import com.github.vladbaton.resource.pojo.UsersForAdminResponse;
import com.github.vladbaton.exception.AdminDeletionException;
import com.github.vladbaton.exception.UserNotFoundByIdException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.github.vladbaton.service.AdminService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Ресурс администратора", description = "Позволяет осуществлять админские обязанности: просматривать юзеров и удалять юзеров")
public class AdminResource {
    @Inject
    AdminService adminService;

    @GET
    @RolesAllowed("Admin")
    @Operation(description = "Просмотр данных по всем юзерам")
    @APIResponse(responseCode = "200", description = "Был получен список юзеров")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве админа")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    public Response getListOfUsers() {
        return Response.ok(new UsersForAdminResponse(adminService.getListOfUsers())).build();
    }

    @Path("/{id}")
    @GET
    @RolesAllowed("Admin")
    @Operation(description = "Просмотр данных о конкретном юзере")
    @Parameter(name = "id", description = "id юзера")
    @APIResponse(responseCode = "200", description = "Была получена информация о юзере")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве админа")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "404", description = "Пользователь с введённым id не найден")
    public Response getUserById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK).entity(new UserForAdminResponse(adminService.getUserById(id))).build();
    }

    @Path("/{id}")
    @DELETE
    @RolesAllowed("Admin")
    @Operation(description = "Удаление юзера с данным id")
    @Parameter(name = "id", description = "id юзера")
    @APIResponse(responseCode = "410", description = "Данные о пользователе и также его документы удалены")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве админа")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "404", description = "Пользователь с введённым id не найден")
    @APIResponse(responseCode = "404", description = "Пользователь с введённым id не найден")
    @APIResponse(responseCode = "400", description = "Попытка удалить админа")
    @APIResponse(responseCode = "500", description = "Не удалось удалить файлы пользователя. На сервере может быть насрано")
    public Response deleteUserById(@PathParam("id") Long id)
            throws UserNotFoundByIdException, AdminDeletionException {
        adminService.deleteUserById(id);
        return Response.status(Response.Status.GONE).build();
    }

    @Path("/paginated")
    @GET
    @RolesAllowed("Admin")
    public Response getUsersPaginated(PaginatedUsersForAdminRequest request) {
        return Response
                .ok(new UsersForAdminResponse(adminService.getPaginatedUsers(request.getPageSize(), request.getPage(), request.getSortBy())))
                .build();
    }
}
