package com.github.vladbaton.repository;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.nio.file.Paths;

@ApplicationScoped
public class DocRepository implements PanacheRepository<Doc> {
    @ConfigProperty(name = "upload.directory")
    String uploadDirectory;

    public Doc findByUserAndFilename(User user, String filename) {
        String absolutePath = Paths.get(
                uploadDirectory + File.separator + user.getUsername() + File.separator + filename
        ).toAbsolutePath().toString();
        return find("user = ?1 and fileReference = ?2", user, absolutePath).firstResult();
    }
}
