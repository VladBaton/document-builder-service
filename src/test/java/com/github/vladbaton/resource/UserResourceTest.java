package com.github.vladbaton.resource;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.UserDTO;
import com.github.vladbaton.resource.pojo.UserForUserResponse;
import com.github.vladbaton.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyViolationExceptionImpl;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.ValidationException;
import org.hibernate.annotations.CurrentTimestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wildfly.security.password.interfaces.SimpleDigestPassword;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserResourceTest {

    @InjectMock
    UserRepository userRepository;

    @InjectMock
    UserService userService;

    @Inject
    UserResource userResource;

    private User user;
    private Set<Doc> docs;
    private String authentificationHeader = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("ABCabcABC123123");
        user.setEmail("ya@ya.ya");
        user.setRole("User");
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        docs = new HashSet<>();
        Doc doc1 = new Doc();
        doc1.setUser(user);
        doc1.setCreatedDate(new Date());
        doc1.setLastUpdatedDate(new Date());
        doc1.setFileReference("fileReference");
        docs.add(doc1);
        user.setDocs(docs);
    }

    @Test
    void registerInvalidDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        userDTO.setEmail("email");
        assertThrows(ResteasyViolationExceptionImpl.class,
                () -> userResource.register(userDTO),
                "Ожидалась ошибка валидации, а получил ты невесть что");
    }

    @Test
    void registerValidDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("ABCabc123");
        userDTO.setEmail("email@email.email");
        Response response = userResource.register(userDTO);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(),
                response.getStatus(),
                "Ожидалось успешное создание пользователя с соответсвующим кодом");
    }

    @TestSecurity(user = "username", roles = {"User"})
    @Test
    void deleteOk() {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        Response response = userResource.delete(authentificationHeader);
        assertNotNull(response);
        assertEquals(Response.Status.GONE.getStatusCode(), response.getStatus());
    }

    @Test
    @TestSecurity(user = "username", roles = {"User"})
    void updateOk() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newusername");
        userDTO.setPassword("ABCabc123");
        userDTO.setEmail("new_email@email.email");
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        Response response = userResource.update(authentificationHeader, userDTO);
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    @TestSecurity(user = "username", roles = {"User"})
    void updateInvalidDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("wrongpassword");
        userDTO.setEmail("wrongemail");
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        assertThrows(ResteasyViolationExceptionImpl.class, () -> userResource.update(authentificationHeader, userDTO));
    }

    @Test
    @TestSecurity(user = "username", roles = {"User"})
    void read() {
        String username = "username";
        Mockito.when(userService.readUser(username)).thenReturn(user);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Response response = userResource.read(authentificationHeader);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        UserForUserResponse userForUserResponse = (UserForUserResponse) response.getEntity();
        assertEquals("username", userForUserResponse.getUsername());
        assertNotEquals("ABCabcABC123123", userForUserResponse.getPassword());
        assertEquals("ya@ya.ya", userForUserResponse.getEmail());
        assertEquals(user.getCreatedDate(), userForUserResponse.getCreatedDate());
        assertEquals(user.getUpdatedDate(), userForUserResponse.getUpdatedDate());
        assertNotNull(user.getDocs());
        assertEquals(docs.size(), user.getDocs().size());
    }
}