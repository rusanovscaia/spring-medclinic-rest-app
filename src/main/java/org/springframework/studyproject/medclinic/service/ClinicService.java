package org.springframework.studyproject.medclinic.service;

import org.springframework.dao.DataAccessException;
import org.springframework.studyproject.medclinic.model.Doctor;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.model.Specialty;
import org.springframework.studyproject.medclinic.model.Visit;

import java.util.Collection;

public interface ClinicService {

    Patient findPatientById(int id) throws DataAccessException;
    Collection<Patient> findPatientByLastName(String lastName) throws DataAccessException;
    Collection<Patient> findAllPatients() throws DataAccessException;
    void savePatient(Patient patient) throws DataAccessException;
    void deletePatient(Patient patient) throws DataAccessException;


    Collection<Visit> findVisitsByPatientId(int patientId);
    Visit findVisitById(int visitId) throws DataAccessException;
    Collection<Visit> findAllVisits() throws DataAccessException;
    void saveVisit(Visit visit) throws DataAccessException;
    void deleteVisit(Visit visit) throws DataAccessException;

    Doctor findDoctorById(int id) throws DataAccessException;
    Collection<Doctor> findDoctors() throws DataAccessException;
    Collection<Doctor> findAllDoctors() throws DataAccessException;
    void saveDoctor(Doctor doctor) throws DataAccessException;
    void deleteDoctor(Doctor doctor) throws DataAccessException;

    Specialty findSpecialtyById(int specialtyId);
    Collection<Specialty> findAllSpecialties() throws DataAccessException;
    void saveSpecialty(Specialty specialty) throws DataAccessException;
    void deleteSpecialty(Specialty specialty) throws DataAccessException;
}
