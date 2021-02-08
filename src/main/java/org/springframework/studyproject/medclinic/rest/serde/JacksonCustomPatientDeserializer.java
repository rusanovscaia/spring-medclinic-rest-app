package org.springframework.studyproject.medclinic.rest.serde;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.studyproject.medclinic.model.Patient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JacksonCustomPatientDeserializer extends StdDeserializer<Patient> {
    public JacksonCustomPatientDeserializer(){
        this(null);
    }

    public JacksonCustomPatientDeserializer(Class<Patient> t) {
        super(t);
    }

    @Override
    public Patient deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birthDate = null;
        JsonNode node = parser.getCodec().readTree(parser);
        Patient patient = new Patient();
        String firstName = node.get("firstName").asText(null);
        String lastName = node.get("lastName").asText(null);
        String address = node.get("address").asText(null);
        String city = node.get("city").asText(null);
        String telephone = node.get("telephone").asText(null);
        String birthDateStr = node.get("birthDate").asText(null);
        try {
            birthDate = formatter.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        if (node.hasNonNull("id")) {
            patient.setId(node.get("id").asInt());
        }
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAddress(address);
        patient.setCity(city);
        patient.setTelephone(telephone);
        patient.setBirthDate(birthDate);
        return patient;
    }

}
