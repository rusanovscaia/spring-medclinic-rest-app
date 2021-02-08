package org.springframework.studyproject.medclinic.security;


import org.springframework.stereotype.Component;


@Component
public class Roles {

    public final String PATIENT_ADMIN = "ROLE_PATIENT_ADMIN";
    public final String DOC_ADMIN = "ROLE_DOC_ADMIN";
    public final String ADMIN = "ROLE_ADMIN";
}
