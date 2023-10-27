package com.mongodb.quickstart.javaspringbootcsfle.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

/**
 * This is the entity class for the "companies" collection.
 * The SpEL expression of the @Encrypted annotation is used to determine the DEK's keyId to use for the encryption.
 *
 * @see com.mongodb.quickstart.javaspringbootcsfle.components.EntitySpelEvaluationExtension
 */
@Document("companies")
@Encrypted(keyId = "#{mongocrypt.keyId(#target)}")
public class CompanyEntity {
    @Id
    private ObjectId id;
    private String name;
    @Encrypted(algorithm = "AEAD_AES_256_CBC_HMAC_SHA_512-Random")
    private Long money;

    public CompanyEntity() {
    }

    public CompanyEntity(ObjectId id, String name, Long money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" + "id=" + id + ", name='" + name + '\'' + ", money='" + money + '\'' + '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
