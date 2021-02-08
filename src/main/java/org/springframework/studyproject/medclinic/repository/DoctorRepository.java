package org.springframework.studyproject.medclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.studyproject.medclinic.model.Doctor;

import java.util.Collection;

public interface DoctorRepository {

    Collection<Doctor> findAll() throws DataAccessException;

    Doctor findById(int id) throws DataAccessException;

    void save(Doctor doctor) throws DataAccessException;

    void delete(Doctor doctor) throws DataAccessException;
}
