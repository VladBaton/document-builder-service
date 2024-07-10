package com.github.vladbaton.resource;


import com.github.vladbaton.exception.WrongAuthorizationHeaderException;
import com.github.vladbaton.mapper.UserMapper;
import com.github.vladbaton.resource.dto.UserDTO;
import com.github.vladbaton.resource.pojo.SlaveForUserResponse;
import com.github.vladbaton.resource.pojo.UserForUserResponse;
import com.github.vladbaton.service.DocService;
import com.github.vladbaton.service.SlaveService;
import com.github.vladbaton.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.print.attribute.standard.Media;
import java.awt.*;

import static com.github.vladbaton.help.AuthChecker.checkBasicAuth;

@Tag(description = "Ресурс для работы с подчинёнными")
@Path("/slave")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SlaveResource {
    @Inject
    SlaveService slaveService;

    @POST
    @Path("/upload")
    @RolesAllowed("User")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(description = "Добавляет подчинённых из файла")
    public Response searchForSlaves(@MultipartForm MultipartFormDataInput inputForm,
                                    @HeaderParam("Authorization") String authorizationToken)
            throws WrongAuthorizationHeaderException {
        slaveService.searchForSlaves(inputForm);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/register")
    @RolesAllowed("Slave")
    @Operation(description = "Осуществляет регистрацию подчинённого в качестве полноправного юзера")
    public Response register(@HeaderParam("Authorization") String authorizationHeader, UserDTO userDTO) {
        slaveService.updateSlave(checkBasicAuth(authorizationHeader)[0], userDTO);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("User")
    @Operation(description = "Выводит список подчинённых данного юзера")
    public Response getSlaves(@HeaderParam("Authorization") String authorizationHeader) {
        return Response.ok(slaveService.getSlaves(checkBasicAuth(authorizationHeader)[0]).stream().map(SlaveForUserResponse::new)).build();
    }

}
