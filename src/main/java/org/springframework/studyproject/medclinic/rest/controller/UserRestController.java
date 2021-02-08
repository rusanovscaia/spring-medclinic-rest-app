package org.springframework.studyproject.medclinic.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.studyproject.medclinic.model.User;
import org.springframework.studyproject.medclinic.rest.BindingErrorsResponse;
import org.springframework.studyproject.medclinic.service.UserServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/users")
public class UserRestController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user, BindingResult bindingResult) throws Exception {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (user == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<User>(user, headers, HttpStatus.BAD_REQUEST);
        }

        this.userServiceImpl.saveUser(user);
        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
    }
}
