package org.springframework.studyproject.medclinic.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.rest.BindingErrorsResponse;
import org.springframework.studyproject.medclinic.service.ClinicService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/patients")
public class PatientRestController {

    @Autowired
    private ClinicService clinicService;

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "/*/lastname/{lastName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Patient>> getPatientsList(@PathVariable("lastName") String patientLastName) {
        if (patientLastName == null) {
            patientLastName = "";
        }
        Collection<Patient> patients = this.clinicService.findPatientByLastName(patientLastName);
        if (patients.isEmpty()) {
            return new ResponseEntity<Collection<Patient>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Patient>>(patients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Patient>> getPatients() {
        Collection<Patient> patients = this.clinicService.findAllPatients();
        if (patients.isEmpty()) {
            return new ResponseEntity<Collection<Patient>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Patient>>(patients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "/{patientId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Patient> getPatient(@PathVariable("patientId") int patientId) {
        Patient patient = null;
        patient = this.clinicService.findPatientById(patientId);
        if (patient == null) {
            return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Patient>(patient, HttpStatus.OK);
    }
    @PreAuthorize( "hasRole(@roles.PATIENT_ADMIN)" )
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Patient> addPatient(@RequestBody @Valid Patient patient, BindingResult bindingResult,
                                          UriComponentsBuilder ucBuilder) {
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || patient.getId() != null) {
            BindingErrorsResponse errors = new BindingErrorsResponse(patient.getId());
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Patient>(headers, HttpStatus.BAD_REQUEST);
        }
        this.clinicService.savePatient(patient);
        headers.setLocation(ucBuilder.path("/api/patients/{id}").buildAndExpand(patient.getId()).toUri());
        return new ResponseEntity<Patient>(patient, headers, HttpStatus.CREATED);
    }

    @PreAuthorize( "hasRole(@roles.PATIENT_ADMIN)" )
    @RequestMapping(value = "/{patientId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Patient> updatePatient(@PathVariable("patientId") int patientId, @RequestBody @Valid Patient patient,
                                             BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        boolean bodyIdMatchesPathId = patient.getId() == null || patientId == patient.getId();
        if (bindingResult.hasErrors() || !bodyIdMatchesPathId) {
            BindingErrorsResponse errors = new BindingErrorsResponse(patientId, patient.getId());
            errors.addAllErrors(bindingResult);
            HttpHeaders headers = new HttpHeaders();
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Patient>(headers, HttpStatus.BAD_REQUEST);
        }
        Patient currentPatient = this.clinicService.findPatientById(patientId);
        if (currentPatient == null) {
            return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
        }
        currentPatient.setAddress(patient.getAddress());
        currentPatient.setCity(patient.getCity());
        currentPatient.setFirstName(patient.getFirstName());
        currentPatient.setLastName(patient.getLastName());
        currentPatient.setTelephone(patient.getTelephone());
        this.clinicService.savePatient(currentPatient);
        return new ResponseEntity<Patient>(currentPatient, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize( "hasRole(@roles.PATIENT_ADMIN)" )
    @RequestMapping(value = "/{patientId}", method = RequestMethod.DELETE, produces = "application/json")
    @Transactional
    public ResponseEntity<Void> deletePatient(@PathVariable("patientId") int patientId) {
        Patient patient = this.clinicService.findPatientById(patientId);
        if (patient == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deletePatient(patient);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
