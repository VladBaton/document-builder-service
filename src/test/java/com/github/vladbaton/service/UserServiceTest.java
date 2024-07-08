package com.github.vladbaton.service;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.entity.builder.UserBuilder;
import com.github.vladbaton.exception.UserNotFoundByUsernameException;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.UserDTO;
import io.quarkus.logging.Log;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserServiceTest {

    @InjectMock
    UserRepository mockUserRepository;

    @Inject
    UserService userService;

    User someUser  = (new UserBuilder())
            .setId(1L)
            .setUsername("username")
            .setPassword("123ABCabc")
            .setEmail("useremail@email.ru")
            .setRole("User")
            .build();

    @BeforeEach
    void setUp() {

    }

    @Test
    void registerUserOk() {
        UserDTO someUserDTO = new UserDTO(someUser);
        //userService.registerUser(someUserDTO);
        //Mockito.verify(mockUserRepository).persistAndFlush(Mockito.any());
        //Mockito.verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void deleteUserOk() {
        Mockito.when(mockUserRepository.findByUsername(someUser.getUsername())).thenReturn(Optional.of(someUser));
        userService.deleteUser(someUser.getUsername());
        Mockito.verify(mockUserRepository).findByUsername(Mockito.any());
        Mockito.verify(mockUserRepository).delete(Mockito.any());
        Mockito.verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void updateUser() {
    }

    @Test
    void readUser() {
    }
}