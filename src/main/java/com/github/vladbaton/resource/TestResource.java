package com.github.vladbaton.resource;

import com.github.vladbaton.resource.pojo.test.UsersForTestResponse;
import com.github.vladbaton.service.AdminService;
import com.github.vladbaton.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
    @Inject
    UserService userService;

    @Inject
    AdminService adminService;

    @POST
    public Response createUsers() {
        userService.addTestUsers();
        return Response.ok().build();
    }

    @GET
    @Path("/{number}")
    public Response getUser(@PathParam("number") Long number) {
        return Response.ok(userService.findByRandomStuff(number)).build();
    }

    @GET
    @Path("/all")
    public Response getAllUsersWithDocs() {
        return Response.ok(new UsersForTestResponse(adminService.getListOfUsers())).build();
    }
}
