package com.mongodb.quickstart.javaspringbootcsfle.model;

import com.mongodb.MongoNamespace;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

@Document("persons")
@Encrypted(keyId = "#{mongocrypt.keyId(#target)}")
public class PersonEntity extends EncryptedEntity {
    static { // todo remove?
        NAMESPACE = new MongoNamespace("test", "persons");
        ENTITY_CLASS = PersonEntity.class;
        DEK_NAME = "personDEK";
    }

    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    //    @Indexed(unique = true) // todo index or not index? Managed by Spring Data or not?
    @Encrypted(algorithm = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic")
    private String ssn;
    @Encrypted(algorithm = "AEAD_AES_256_CBC_HMAC_SHA_512-Random")
    private String bloodType;

    public PersonEntity() {
        super();
    }

    public PersonEntity(ObjectId id, String firstName, String lastName, String ssn, String bloodType) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.bloodType = bloodType;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", ssn='" + ssn + '\'' + ", bloodType='" + bloodType + '\'' + '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

}
