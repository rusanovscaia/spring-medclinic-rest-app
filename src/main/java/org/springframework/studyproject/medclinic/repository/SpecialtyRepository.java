package org.springframework.studyproject.medclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.studyproject.medclinic.model.Specialty;

import java.util.Collection;


public interface SpecialtyRepository {

    Specialty findById(int id) throws DataAccessException;

    Collection<Specialty> findAll() throws DataAccessException;

    void save(Specialty specialty) throws DataAccessException;

    void delete(Specialty specialty) throws DataAccessException;

}
