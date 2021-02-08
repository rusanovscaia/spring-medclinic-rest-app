package org.springframework.studyproject.medclinic.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.studyproject.medclinic.repository.DoctorRepository;
import org.springframework.studyproject.medclinic.model.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;


@Repository
@Profile("jpa")
public class JpaDoctorRepositoryImpl implements DoctorRepository {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Doctor> findAll() throws DataAccessException {
        return this.em.createQuery("SELECT doctor FROM Doctor doctor").getResultList();
    }

    @Override
    public Doctor findById(int id) throws DataAccessException {
        return this.em.find(Doctor.class, id);
    }

    @Override
    public void save(Doctor doctor) throws DataAccessException {
        if (doctor.getId() == null) {
            this.em.persist(doctor);
        } else {
            this.em.merge(doctor);
        }
    }

    @Override
    public void delete(Doctor doctor) throws DataAccessException {
        this.em.remove(this.em.contains(doctor) ? doctor : this.em.merge(doctor));
    }
}
