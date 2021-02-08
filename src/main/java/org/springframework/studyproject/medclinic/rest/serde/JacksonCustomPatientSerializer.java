package org.springframework.studyproject.medclinic.rest.serde;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.model.Visit;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class JacksonCustomPatientSerializer extends StdSerializer<Patient> {

    public JacksonCustomPatientSerializer() {
        this(null);
    }

    public JacksonCustomPatientSerializer(Class<Patient> t) {
        super(t);
    }

    @Override
    public void serialize(Patient patient, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject(); //patient
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
        // write visits array
        jgen.writeArrayFieldStart("visits");
        for (Visit visit : patient.getVisits()) {
            jgen.writeStartObject(); // visit
            if (visit.getId() == null) {
                jgen.writeNullField("id");
            } else {
                jgen.writeNumberField("id", visit.getId());
            }
            jgen.writeStringField("date", formatter.format(visit.getDate()));
            jgen.writeStringField("description", visit.getDescription());
            jgen.writeNumberField("doctor", visit.getDoctor().getId());
            jgen.writeEndObject(); // visit
        }
        jgen.writeEndArray(); // visits
        jgen.writeEndObject(); // patients
    }
}
