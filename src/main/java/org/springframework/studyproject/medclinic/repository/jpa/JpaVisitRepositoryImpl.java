package org.springframework.studyproject.medclinic.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.studyproject.medclinic.model.Visit;
import org.springframework.studyproject.medclinic.repository.VisitRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;


@Repository
@Profile("jpa")
public class JpaVisitRepositoryImpl implements VisitRepository {


    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Visit visit) {
        if (visit.getId() == null) {
            this.em.persist(visit);
        } else {
            this.em.merge(visit);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Visit> findByPatientId(Integer patientId) {
        Query query = this.em.createQuery("SELECT v FROM Visit v where v.patient.id= :id");
        query.setParameter("id", patientId);
        return query.getResultList();
    }

    @Override
    public Visit findById(int id) throws DataAccessException {
        return this.em.find(Visit.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Visit> findAll() throws DataAccessException {
        return this.em.createQuery("SELECT v FROM Visit v").getResultList();
    }

    @Override
    public void delete(Visit visit) throws DataAccessException {
        this.em.remove(this.em.contains(visit) ? visit : this.em.merge(visit));
    }

}
