package com.github.vladbaton.repository;

import com.github.vladbaton.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String username) {
        return find("username", username).singleResultOptional();
    }

    public List<User> findByPassword(String password) {
        return find("password", password).list();
    }

    public List<User> findByRandomStuff(Long randomStuff) {
        long start = System.currentTimeMillis();
        List<User> result = find("randomStuff", randomStuff).list();
        long finish = System.currentTimeMillis();
        Log.error("Поиск занял " + (finish - start) + " ms");
        return result;
    }
}
