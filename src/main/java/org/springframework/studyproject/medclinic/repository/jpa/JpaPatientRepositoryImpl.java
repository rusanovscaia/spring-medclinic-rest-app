package org.springframework.studyproject.medclinic.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.repository.PatientRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
@Profile("jpa")
public class JpaPatientRepositoryImpl implements PatientRepository {


    @PersistenceContext
    private EntityManager em;


    @SuppressWarnings("unchecked")
    public Collection<Patient> findByLastName(String lastName) {
        Query query = this.em.createQuery("SELECT DISTINCT patient FROM Patient patient WHERE patient.lastName LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    @Override
    public Patient findById(int id) {
        Query query = this.em.createQuery("SELECT patient FROM Patient patient WHERE patient.id =:id");
        query.setParameter("id", id);
        return (Patient) query.getSingleResult();
    }

    @Override
    public void save(Patient patient) {
        if (patient.getId() == null) {
            this.em.persist(patient);
        } else {
            this.em.merge(patient);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Patient> findAll() throws DataAccessException {
        Query query = this.em.createQuery("SELECT patient FROM Patient patient");
        return query.getResultList();
    }

    @Override
    public void delete(Patient patient) throws DataAccessException {
        this.em.remove(this.em.contains(patient) ? patient : this.em.merge(patient));
    }
}
