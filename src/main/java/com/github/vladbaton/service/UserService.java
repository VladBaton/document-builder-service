package com.github.vladbaton.service;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.repository.DocRepository;
import com.github.vladbaton.repository.UserRepository;
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
import java.util.Set;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Transactional(rollbackOn = ConstraintViolationException.class)
    public void registerUser(User user) throws ConstraintViolationException {
        user.setRole("User");
        userRepository.persistAndFlush(user);
    }

    @Transactional(rollbackOn = UserNotFoundByUsernameException.class)
    public void deleteUser(String username) throws UserNotFoundByUsernameException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserNotFoundByUsernameException(username);
        else if (user.getRole().equals("Admin"))
            throw new AdminDeletionException(user.getUserId());
        userRepository.delete(user);
    }

    @Transactional(rollbackOn = {ConstraintViolationException.class, WrongAuthorizationHeaderException.class})
    public void updateUser(String username, User user)
            throws ConstraintViolationException, UserNotFoundByUsernameException {
        User updateUser = userRepository.findByUsername(username);
        if (updateUser == null)
            throw new UserNotFoundByUsernameException(username);
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setEmail(user.getEmail());
        userRepository.persistAndFlush(updateUser);
    }

    public User readUser(String username) throws UserNotFoundByUsernameException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        }
        return user;
    }
}

