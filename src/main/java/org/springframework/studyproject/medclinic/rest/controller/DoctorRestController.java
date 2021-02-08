package org.springframework.studyproject.medclinic.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.studyproject.medclinic.model.Doctor;
import org.springframework.studyproject.medclinic.model.Specialty;
import org.springframework.studyproject.medclinic.rest.BindingErrorsResponse;
import org.springframework.studyproject.medclinic.service.ClinicService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/doctors")
public class DoctorRestController {

    @Autowired
    private ClinicService clinicService;

    @PreAuthorize( "hasRole(@roles.DOC_ADMIN)" )
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Doctor>> getAllDoctors(){
        Collection<Doctor> doctors = new ArrayList<Doctor>();
        doctors.addAll(this.clinicService.findAllDoctors());
        if (doctors.isEmpty()){
            return new ResponseEntity<Collection<Doctor>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Doctor>>(doctors, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.DOC_ADMIN)" )
    @RequestMapping(value = "/{doctorId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("doctorId") int doctorId){
        Doctor doctor = this.clinicService.findDoctorById(doctorId);
        if(doctor == null){
            return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.DOC_ADMIN)" )
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Doctor> addDoctor(@RequestBody @Valid Doctor doctor, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (doctor == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Doctor>(headers, HttpStatus.BAD_REQUEST);
        }
        this.clinicService.saveDoctor(doctor);
        headers.setLocation(ucBuilder.path("/api/doctors/{id}").buildAndExpand(doctor.getId()).toUri());
        return new ResponseEntity<Doctor>(doctor, headers, HttpStatus.CREATED);
    }

    @PreAuthorize( "hasRole(@roles.DOC_ADMIN)" )
    @RequestMapping(value = "/{doctorId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable("doctorId") int doctorId, @RequestBody @Valid Doctor doctor, BindingResult bindingResult){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (doctor == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Doctor>(headers, HttpStatus.BAD_REQUEST);
        }
        Doctor currentDoctor = this.clinicService.findDoctorById(doctorId);
        if(currentDoctor == null){
            return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
        }
        currentDoctor.setFirstName(doctor.getFirstName());
        currentDoctor.setLastName(doctor.getLastName());
        currentDoctor.clearSpecialties();
        for(Specialty spec : doctor.getSpecialties()) {
            currentDoctor.addSpecialty(spec);
        }
        this.clinicService.saveDoctor(currentDoctor);
        return new ResponseEntity<Doctor>(currentDoctor, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize( "hasRole(@roles.DOC_ADMIN)" )
    @RequestMapping(value = "/{doctorId}", method = RequestMethod.DELETE, produces = "application/json")
    @Transactional
    public ResponseEntity<Void> deleteDoctor(@PathVariable("doctorId") int doctorId){
        Doctor doctor = this.clinicService.findDoctorById(doctorId);
        if(doctor == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteDoctor(doctor);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


}
