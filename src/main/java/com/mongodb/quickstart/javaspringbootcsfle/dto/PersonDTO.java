package com.mongodb.quickstart.javaspringbootcsfle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import org.bson.types.ObjectId;

public record PersonDTO(
        String id,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        String ssn,
        @JsonProperty("blood_type") String bloodType) {

    public PersonDTO(PersonEntity personSaved) {
        this(personSaved.getId().toHexString(), personSaved.getFirstName(), personSaved.getLastName(),
             personSaved.getSsn(), personSaved.getBloodType());
    }

    public PersonEntity toPersonEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new PersonEntity(_id, firstName, lastName, ssn, bloodType);
    }

}
