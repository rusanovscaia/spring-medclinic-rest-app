package org.springframework.studyproject.service.clinicService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.studyproject.medclinic.model.Specialty;
import org.springframework.studyproject.medclinic.service.ClinicService;
import org.springframework.studyproject.medclinic.util.EntityUtils;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractClinicServiceTests {

    @Autowired
    protected ClinicService clinicService;


    @Test
    public void shouldFindSpecialtyById(){
        Specialty specialty = this.clinicService.findSpecialtyById(1);
        assertThat(specialty.getName()).isEqualTo("radiology");
    }

    @Test
    public void shouldFindAllSpecialties(){
        Collection<Specialty> specialties = this.clinicService.findAllSpecialties();
        Specialty specialty1 = EntityUtils.getById(specialties, Specialty.class, 1);
        assertThat(specialty1.getName()).isEqualTo("radiology");
        Specialty specialty3 = EntityUtils.getById(specialties, Specialty.class, 3);
        assertThat(specialty3.getName()).isEqualTo("mamology");
    }

}
