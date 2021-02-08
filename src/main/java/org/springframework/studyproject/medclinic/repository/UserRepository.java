package org.springframework.studyproject.medclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.studyproject.medclinic.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user) throws DataAccessException;
    Optional<User> findByUsername(String username);
}
