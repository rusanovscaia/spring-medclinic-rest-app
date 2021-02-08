package org.springframework.studyproject.medclinic.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.studyproject.medclinic.model.Specialty;
import org.springframework.studyproject.medclinic.repository.SpecialtyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
@Profile("jpa")
public class JpaSpecialityRepositoryImpl implements SpecialtyRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Specialty findById(int id) {
        return this.em.find(Specialty.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Specialty> findAll() throws DataAccessException {
        return this.em.createQuery("SELECT s FROM Specialty s").getResultList();
    }

    @Override
    public void save(Specialty specialty) throws DataAccessException {
        if (specialty.getId() == null) {
            this.em.persist(specialty);
        } else {
            this.em.merge(specialty);
        }
    }

    @Override
    public void delete(Specialty specialty) throws DataAccessException {
        this.em.remove(this.em.contains(specialty) ? specialty : this.em.merge(specialty));
        Integer specId = specialty.getId();
        this.em.createNativeQuery("DELETE FROM doc_specialties WHERE specialty_id=" + specId).executeUpdate();
        this.em.createQuery("DELETE FROM Specialty specialty WHERE id=" + specId).executeUpdate();
    }

}
