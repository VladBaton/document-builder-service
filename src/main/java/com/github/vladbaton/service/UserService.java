package com.github.vladbaton.service;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.repository.DocRepository;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MultivaluedMap;
import org.apache.poi.util.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Transactional(rollbackOn = ConstraintViolationException.class)
    public void registerUser(UserDTO user) throws ConstraintViolationException {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setRole("User");
        userRepository.persist(newUser);
        userRepository.flush();
    }

    @Transactional(rollbackOn = UserNotFoundByUsernameException.class)
    public void deleteUser(String username) throws UserNotFoundByUsernameException {
        userRepository.findByUsername(username).ifPresentOrElse(
                (foundUser) -> {
                    if (foundUser.getRole().equals("Admin"))
                        throw new AdminDeletionException(foundUser.getUserId());
                    userRepository.delete(foundUser);
                },
                () -> {
                    throw new UserNotFoundByUsernameException(username);
                }
        );
    }

    @Transactional(rollbackOn = {ConstraintViolationException.class, WrongAuthorizationHeaderException.class})
    public void updateUser(String username, User user)
            throws ConstraintViolationException, UserNotFoundByUsernameException {
        userRepository.findByUsername(username).ifPresentOrElse(
                (foundUser) -> {
                    foundUser.setUsername(user.getUsername());
                    foundUser.setPassword(user.getPassword());
                    foundUser.setEmail(user.getEmail());
                    userRepository.persistAndFlush(foundUser);
                },
                () -> {
                    throw new UserNotFoundByUsernameException(username);
                }
        );
    }

    public User readUser(String username) throws UserNotFoundByUsernameException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundByUsernameException(username));
    }
}

