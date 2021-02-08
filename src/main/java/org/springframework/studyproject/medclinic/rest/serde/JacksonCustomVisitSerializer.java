package org.springframework.studyproject.medclinic.rest.serde;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.studyproject.medclinic.model.Doctor;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.model.Visit;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class JacksonCustomVisitSerializer extends StdSerializer<Visit> {

    public JacksonCustomVisitSerializer() {
        this(null);
    }

    protected JacksonCustomVisitSerializer(Class<Visit> t) {
        super(t);
    }

    @Override
    public void serialize(Visit visit, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if ((visit == null) || (visit.getPatient() == null)) {
            throw new IOException("Cannot serialize Visit object - visit or visit.patient is null");
        }
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject(); // visit
        if (visit.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", visit.getId());
        }
        jgen.writeStringField("date", formatter.format(visit.getDate()));
        jgen.writeStringField("description", visit.getDescription());

        Patient patient = visit.getPatient();
        jgen.writeObjectFieldStart("patient");
        if (patient.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", patient.getId());
        }
        jgen.writeStringField("firstName", patient.getFirstName());
        jgen.writeStringField("lastName", patient.getLastName());
        jgen.writeStringField("address", patient.getAddress());
        jgen.writeStringField("city", patient.getCity());
        jgen.writeStringField("telephone", patient.getTelephone());
        jgen.writeStringField("birthDate", formatter.format(patient.getBirthDate()));

        Doctor doctor = visit.getDoctor();
        jgen.writeObjectFieldStart("doctor");
        if (doctor.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", doctor.getId());
        }
        jgen.writeStringField("firstName", doctor.getFirstName());
        jgen.writeStringField("lastName", doctor.getLastName());
        jgen.writeEndObject(); //patient
        jgen.writeEndObject(); //doctor
        jgen.writeEndObject(); //visit

    }

}
