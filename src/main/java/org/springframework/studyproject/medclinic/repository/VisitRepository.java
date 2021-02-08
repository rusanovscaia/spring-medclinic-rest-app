package org.springframework.studyproject.medclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.studyproject.medclinic.model.Visit;

import java.util.Collection;
import java.util.List;

public interface VisitRepository {

    void save(Visit visit) throws DataAccessException;

    List<Visit> findByPatientId(Integer patientId);

    Visit findById(int id) throws DataAccessException;

    Collection<Visit> findAll() throws DataAccessException;

    void delete(Visit visit) throws DataAccessException;
}
