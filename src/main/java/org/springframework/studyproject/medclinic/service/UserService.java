package org.springframework.studyproject.medclinic.service;

import org.springframework.studyproject.medclinic.model.User;

public interface UserService {
    void saveUser(User user) throws Exception;
}
