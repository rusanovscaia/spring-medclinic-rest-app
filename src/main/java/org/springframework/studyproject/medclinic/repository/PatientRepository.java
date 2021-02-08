package org.springframework.studyproject.medclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.studyproject.medclinic.model.Patient;

import java.util.Collection;

public interface PatientRepository {

    Collection<Patient> findByLastName(String lastName) throws DataAccessException;
    Patient findById(int id) throws DataAccessException;
    void save(Patient patient) throws DataAccessException;
    Collection<Patient> findAll() throws DataAccessException;
    void delete(Patient patient) throws DataAccessException;
}
