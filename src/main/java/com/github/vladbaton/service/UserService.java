package com.github.vladbaton.service;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.AdminDeletionException;
import com.github.vladbaton.exception.UserNotFoundByUsernameException;
import com.github.vladbaton.exception.WrongAuthorizationHeaderException;
import com.github.vladbaton.mapper.UserMapper;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Transactional(rollbackOn = ConstraintViolationException.class)
    public void registerUser(@Valid UserDTO user) throws ConstraintViolationException {
        User newUser = userMapper.toEntity(user);
        newUser.setRole("User");
        userRepository.persistAndFlush(newUser);
    }

    @Transactional(rollbackOn = Exception.class)
    public void registerUsers(@Valid List<UserDTO> users) throws ConstraintViolationException {
        EntityManager manager = userRepository.getEntityManager();
        for (int i = 1; i <= users.size(); i++) {
            manager.persist(userMapper.toEntity(users.get(i - 1)));
            if(i % 50 == 0) {
                manager.flush();
                manager.clear();
            }
        }
        manager.flush();
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

