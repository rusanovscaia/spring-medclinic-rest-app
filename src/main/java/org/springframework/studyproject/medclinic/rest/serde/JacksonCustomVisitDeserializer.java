package org.springframework.studyproject.medclinic.rest.serde;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.studyproject.medclinic.model.Doctor;
import org.springframework.studyproject.medclinic.model.Patient;
import org.springframework.studyproject.medclinic.model.Visit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JacksonCustomVisitDeserializer extends StdDeserializer<Visit> {

    public JacksonCustomVisitDeserializer() {
        this(null);
    }

    public JacksonCustomVisitDeserializer(Class<Visit> t) {
        super(t);
    }

    @Override
    public Visit deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Visit visit = new Visit();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        ObjectMapper mapper = new ObjectMapper();
        Date visitDate = null;
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode patient_node = node.get("patient");
        patient = mapper.treeToValue(patient_node, Patient.class);
        JsonNode doctor_node = node.get("doctor");
        doctor = mapper.treeToValue(doctor_node, Doctor.class);
        int visitId = node.get("id").asInt();
        String visitDateStr = node.get("date").asText(null);
        String description = node.get("description").asText(null);
        try {
            visitDate = formatter.parse(visitDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        if (!(visitId == 0)) {
            visit.setId(visitId);
        }
        visit.setDate(visitDate);
        visit.setDescription(description);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        return visit;
    }
}
