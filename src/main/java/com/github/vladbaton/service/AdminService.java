package com.github.vladbaton.service;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.exception.*;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.resource.dto.SortOrder;
import com.github.vladbaton.resource.dto.sortByDTO;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AdminService {
    @Inject
    UserRepository userRepository;

    @ConfigProperty(name = "upload.directory")
    String uploadDirectory;

    public List<User> getListOfUsers() {
        return userRepository.listAll();
    }

    public User getUserById(Long id) throws UserNotFoundByIdException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        }
        return user;
    }

    @Transactional(rollbackOn = {
            UserNotFoundByIdException.class,
            UserNotFoundByUsernameException.class,
            FileDeletionException.class}
    )
    public void deleteUserById(Long id)
            throws UserNotFoundByIdException, AdminDeletionException, FileDeletionException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        } else if (user.getRole().equals("Admin")) {
            throw new AdminDeletionException(id);
        }

        for (Doc doc : user.getDocs()) {
            try {
                Files.delete(Paths.get(doc.getFileReference()));
            } catch (IOException ex) {
                throw new FileDeletionException(doc.getFileReference());
            }
        }

        try {
            Files.delete(Paths.get(uploadDirectory + File.separator + user.getUsername()));
        } catch (IOException ex) {
            throw new FileDeletionException();
        }
        userRepository.delete(user);
    }

    public List<User> getPaginatedUsers(int pageSize, int page, List<sortByDTO> orderBy) {
        if(page < 0 || pageSize < 1 || orderBy == null || orderBy.isEmpty()) {
            throw new BadRequest();
        }
        Sort sort = Sort.empty();
        orderBy.forEach(sortByDTO -> {
            sort.and(sortByDTO.getField(),
                    (sortByDTO.getOrder() == SortOrder.ASC? Sort.Direction.Ascending: Sort.Direction.Descending));
        });
        return ((PanacheQuery<User>) userRepository.findAll(sort)).page(
                page, pageSize
        ).list();
    }
}
