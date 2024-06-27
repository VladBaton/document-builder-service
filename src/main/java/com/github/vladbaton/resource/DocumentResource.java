package com.github.vladbaton.resource;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.resource.pojo.BuildDocumentRequest;
import com.github.vladbaton.resource.pojo.DocForUserResponse;
import com.github.vladbaton.resource.pojo.DocsForUserResponse;
import com.github.vladbaton.service.DocService;
import com.github.vladbaton.service.UserService;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static com.github.vladbaton.help.AuthChecker.checkBasicAuth;

@ApplicationScoped
@Path("/doc")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Ресурс работы с документами", description = "Добавление, удаление, скачивание и построение документов")
public class DocumentResource {
    @Inject
    DocService docService;

    @Path("/upload")
    @POST
    @RolesAllowed("User")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(description = "Загрузка документа на сервер")
    @APIResponse(responseCode = "200", description = "Документ успешно загружен на сервер")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве юзера")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "500", description = "Не удалось загрузить файл")
    public Response uploadDoc(@MultipartForm MultipartFormDataInput input, @HeaderParam("Authorization") String authorizationToken)
            throws FailedToUploadFileException, WrongAuthorizationHeaderException {
        docService.uploadDoc(input, checkBasicAuth(authorizationToken)[0]);
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("{filename}")
    @GET
    @RolesAllowed("User")
    @Operation(description = "Просмотр данных о документе по его ID")
    @APIResponse(responseCode = "200", description = "Была получена инфа по документу")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве юзера")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "404", description = "Файл не найден")
    public Response getDocByUsernameAndFilename(@HeaderParam("Authorization") String authorizationToken, @PathParam("filename") String filename)
            throws UserNotFoundByUsernameException, FileNotFoundException, WrongAuthorizationHeaderException {
        return Response.ok(new DocForUserResponse(docService.getDocByUsernameAndFilename(checkBasicAuth(authorizationToken)[0], filename))).build();
    }

    @Path("/all")
    @GET
    @RolesAllowed("User")
    @Operation(description = "Просмотр данных о всех документа юзера")
    @APIResponse(responseCode = "200", description = "Был получен список доков")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве админа")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    public Response getListOfDocs(@HeaderParam("Authorization") String authorizationToken)
            throws WrongAuthorizationHeaderException {
        return Response.ok(new DocsForUserResponse(docService.getListOfDocs(checkBasicAuth(authorizationToken)[0]))).build();
    }

    @Path("/download/{filename}")
    @GET
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Operation(description = "Скачать шаблон документа с сервера")
    @Parameter(name = "filename", description = "Название шаблона документа")
    @APIResponse(responseCode = "200", description = "Успешное скачивание")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве админа")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "404", description = "Документ не найден")
    public Response downloadDocument(@HeaderParam("Authorization") String authorizationToken,
                                     @PathParam("filename") String filename)
            throws UserNotFoundByUsernameException, FileNotFoundException,
            WrongAuthorizationHeaderException, FailedToDownloadFileException {
        InputStream fileStream = docService.downloadDoc(checkBasicAuth(authorizationToken)[0], filename);
        return Response.ok(fileStream)
                .header("Content-Disposition", "attachment;filename=\"" + filename + "\"")
                .build();
    }

    @Path("/build/{filename}")
    @POST
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Operation(description = "Построить документ")
    @Parameter(name = "filename", description = "имя файла-шаблона для построения документа")
    @APIResponse(responseCode = "200", description = "Был получен список юзеров")
    @APIResponse(responseCode = "401", description = "Пользователь не авторизован в качестве админа")
    @APIResponse(responseCode = "403", description = "Пользователь обращается к несуществующему API")
    @APIResponse(responseCode = "404", description = "Документ не найден")
    @APIResponse(responseCode = "500", description = "Не удалось построить документ")
    public Response buildDocument(@HeaderParam("Authorization") String authorizationToken,
                                  @PathParam("filename") String filename,
                                  BuildDocumentRequest request)
            throws UserNotFoundByUsernameException, FileNotFoundException,
            WrongAuthorizationHeaderException, FailedToDownloadFileException {
        ByteArrayOutputStream builtDocument = docService.buildDocument(checkBasicAuth(authorizationToken)[0], filename, request.getTargetsReplacements());
        return Response.ok(builtDocument.toByteArray())
                .header("Content-Disposition", "attachment;filename=\"" + filename)
                .build();
    }
}
