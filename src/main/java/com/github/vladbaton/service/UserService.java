package com.github.vladbaton.service;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.entity.builder.UserBuilder;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Transactional(rollbackOn = ConstraintViolationException.class)
    public void registerUser(@Valid UserDTO user) throws ConstraintViolationException {
        User newUser = (new UserBuilder())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setPatronymic(user.getPatronymic())
                .setPhone(user.getPhone())
                .setDirector(user.getDirectorId())
                .setRole("User")
                .build();
        userRepository.persistAndFlush(newUser);
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
    public void updateUser(String username, UserDTO user)
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

