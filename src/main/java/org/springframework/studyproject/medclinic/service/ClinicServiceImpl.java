package org.springframework.studyproject.medclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.studyproject.medclinic.model.Doctor;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.model.Specialty;
import org.springframework.studyproject.medclinic.model.Visit;
import org.springframework.studyproject.medclinic.repository.DoctorRepository;
import org.springframework.studyproject.medclinic.repository.PatientRepository;
import org.springframework.studyproject.medclinic.repository.SpecialtyRepository;
import org.springframework.studyproject.medclinic.repository.VisitRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ClinicServiceImpl implements ClinicService {


    private SpecialtyRepository specialtyRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private VisitRepository visitRepository;

    @Autowired
    public ClinicServiceImpl(SpecialtyRepository specialtyRepository,
                             DoctorRepository doctorRepository,
                             PatientRepository patientRepository,
                             VisitRepository visitRepository) {
        this.specialtyRepository = specialtyRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Patient findPatientById(int id) throws DataAccessException {
        Patient patient = null;
        try {
            patient = patientRepository.findById(id);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            return null;
        }
        return patient;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Patient> findPatientByLastName(String lastName) throws DataAccessException {
        return patientRepository.findByLastName(lastName);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<Patient> findAllPatients() throws DataAccessException {
        return patientRepository.findAll();
    }

    @Override
    @Transactional
    public void savePatient(Patient patient) throws DataAccessException {
        patientRepository.save(patient);
    }


    @Override
    @Transactional
    public void deletePatient(Patient patient) throws DataAccessException {
        patientRepository.delete(patient);
    }


    @Override
    @Transactional(readOnly = true)
    public Visit findVisitById(int visitId) throws DataAccessException {
        Visit visit = null;
        try {
            visit = visitRepository.findById(visitId);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            return null;
        }
        return visit;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Visit> findVisitsByPatientId(int patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Visit> findAllVisits() throws DataAccessException {
        return visitRepository.findAll();
    }


    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }

    @Override
    @Transactional
    public void deleteVisit(Visit visit) throws DataAccessException {
        visitRepository.delete(visit);
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor findDoctorById(int id) throws DataAccessException {
        Doctor doctor = null;
        try {
            doctor = doctorRepository.findById(id);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            return null;
        }
        return doctor;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Doctor> findAllDoctors() throws DataAccessException {
        return doctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "doctors")
    public Collection<Doctor> findDoctors() throws DataAccessException {
        return doctorRepository.findAll();
    }

    @Override
    @Transactional
    public void saveDoctor(Doctor doctor) throws DataAccessException {
        doctorRepository.save(doctor);
    }


    @Override
    @Transactional
    public void deleteDoctor(Doctor doctor) throws DataAccessException {
        doctorRepository.delete(doctor);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<Specialty> findAllSpecialties() throws DataAccessException {
        return specialtyRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Specialty findSpecialtyById(int specialtyId) {
        Specialty specialty = null;
        try {
            specialty = specialtyRepository.findById(specialtyId);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            return null;
        }
        return specialty;
    }

    @Override
    @Transactional
    public void saveSpecialty(Specialty specialty) throws DataAccessException {
        specialtyRepository.save(specialty);
    }

    @Override
    @Transactional
    public void deleteSpecialty(Specialty specialty) throws DataAccessException {
        specialtyRepository.delete(specialty);
    }

}

