package org.springframework.studyproject.medclinic.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.studyproject.medclinic.model.Visit;
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
@RequestMapping("api/visits")
public class VisitRestController {

    @Autowired
    private ClinicService clinicService;

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Visit>> getAllVisits() {
        Collection<Visit> visits = new ArrayList<Visit>();
        visits.addAll(this.clinicService.findAllVisits());
        if (visits.isEmpty()) {
            return new ResponseEntity<Collection<Visit>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Visit>>(visits, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "/{visitId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Visit> getVisit(@PathVariable("visitId") int visitId) {
        Visit visit = this.clinicService.findVisitById(visitId);
        if (visit == null) {
            return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Visit>(visit, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Visit> addVisit(@RequestBody @Valid Visit visit, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (visit == null) || (visit.getPatient() == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Visit>(headers, HttpStatus.BAD_REQUEST);
        }
        this.clinicService.saveVisit(visit);
        headers.setLocation(ucBuilder.path("/api/visits/{id}").buildAndExpand(visit.getId()).toUri());
        return new ResponseEntity<Visit>(visit, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "/{visitId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Visit> updateVisit(@PathVariable("visitId") int visitId, @RequestBody @Valid Visit visit, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (visit == null) || (visit.getPatient() == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Visit>(headers, HttpStatus.BAD_REQUEST);
        }
        Visit currentVisit = this.clinicService.findVisitById(visitId);
        if (currentVisit == null) {
            return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
        }
        currentVisit.setDate(visit.getDate());
        currentVisit.setDescription(visit.getDescription());
        currentVisit.setPatient(visit.getPatient());
        currentVisit.setDoctor(visit.getDoctor());
        this.clinicService.saveVisit(currentVisit);
        return new ResponseEntity<Visit>(currentVisit, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.PATIENT_ADMIN)")
    @RequestMapping(value = "/{visitId}", method = RequestMethod.DELETE, produces = "application/json")
    @Transactional
    public ResponseEntity<Void> deleteVisit(@PathVariable("visitId") int visitId) {
        Visit visit = this.clinicService.findVisitById(visitId);
        if (visit == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteVisit(visit);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
