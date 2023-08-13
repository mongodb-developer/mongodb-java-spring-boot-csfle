package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.vault.ClientEncryption;
import jakarta.annotation.PostConstruct;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
@DependsOn("keyVaultCollectionSetup")
public class PersonCollectionSetup {

    private static final MongoNamespace personNamespace = new MongoNamespace("test", "persons");
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCollectionSetup.class);
    private static final String PERSON_DEK_NAME = "personDEK";
    private final ClientEncryption clientEncryption;
    private final MongoClient mongoClient;
    private String personDEKBase64;
    private BsonDocument schemaMap;
    private BsonDocument jsonSchema;

    @Value("${mongodb.kms.provider}")
    private String LOCAL;

    public PersonCollectionSetup(ClientEncryption clientEncryption, MongoClient standardMongoClient) {
        this.clientEncryption = clientEncryption;
        this.mongoClient = standardMongoClient;

    }

    @PostConstruct
    public void postConstruct() {
        this.personDEKBase64 = createOrRetrieveDEK(PERSON_DEK_NAME);
        this.schemaMap = generateSchemaMap(personDEKBase64);
        this.jsonSchema = jsonSchemaWrapper(schemaMap);
        createPersonCollectionIfNecessary();
    }

    private void createPersonCollectionIfNecessary() {
        MongoDatabase db = mongoClient.getDatabase(personNamespace.getDatabaseName());
        String collStr = personNamespace.getCollectionName();
        if (!doesPersonCollectionExist(db)) {
            db.createCollection(collStr, new CreateCollectionOptions().validationOptions(new ValidationOptions().validator(jsonSchema)));
        }
    }

    private String createOrRetrieveDEK(String keyAltName) {
        Base64.Encoder b64Encoder = Base64.getEncoder();
        BsonDocument personDEK = clientEncryption.getKeyByAltName(keyAltName);
        BsonBinary dataKeyId;
        if (personDEK == null) {
            LOGGER.info("=> Creating Data Encryption Key: " + keyAltName);
            DataKeyOptions dko = new DataKeyOptions().keyAltNames(List.of(keyAltName));
            dataKeyId = clientEncryption.createDataKey(LOCAL, dko);
            LOGGER.debug("=> DEK ID: " + dataKeyId);
        } else {
            LOGGER.info("=> Existing Data Encryption Key: " + keyAltName);
            dataKeyId = personDEK.get("_id").asBinary();
            LOGGER.debug("=> DEK ID: " + dataKeyId);
        }
        LOGGER.debug("=> Base64 DEK ID: " + b64Encoder.encodeToString(dataKeyId.getData()));
        return b64Encoder.encodeToString(dataKeyId.getData());
    }

    private BsonDocument generateSchemaMap(String dekId) {
        // todo clean all the new Document(...)
        Document jsonSchema = new Document().append("bsonType", "object")
                                            .append("encryptMetadata", new Document().append("keyId", List.of(new Document().append("$binary", new Document().append("base64", dekId)
                                                                                                                                                             .append("subType", "04")))))
                                            .append("properties", new Document().append("ssn", new Document().append("encrypt", new Document().append("bsonType", "string")
                                                                                                                                              .append("algorithm", "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic")))
                                                                                .append("blood_type", new Document().append("encrypt", new Document().append("bsonType", "string")
                                                                                                                                                     .append("algorithm", "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic"))));

        return BsonDocument.parse(jsonSchema.toJson());
    }

    private BsonDocument jsonSchemaWrapper(BsonDocument jsonSchema) {
        return new BsonDocument("$jsonSchema", jsonSchema);
    }

    private boolean doesPersonCollectionExist(MongoDatabase db) {
        return db.listCollectionNames()
                 .into(new ArrayList<>())
                 .stream()
                 .anyMatch(c -> c.equals(personNamespace.getCollectionName()));
    }

    public MongoNamespace getPersonNamespace() {
        return personNamespace;
    }

    public BsonDocument getSchemaMap() {
        return schemaMap;
    }
}
