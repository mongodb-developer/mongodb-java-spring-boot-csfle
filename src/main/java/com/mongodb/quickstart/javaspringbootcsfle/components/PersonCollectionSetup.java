package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.vault.ClientEncryption;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;

import static java.util.List.of;

@Component
public class PersonCollectionSetup implements EncryptedCollection {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCollectionSetup.class);
    private static final MongoNamespace personNamespace = new MongoNamespace("test", "persons");
    private static final String DEK_NAME = "personDEK";
    private String DEKBase64;
    private BsonDocument schemaMap;

    @Value("${mongodb.kms.provider}")
    private String KMS_PROVIDER;

    @Override
    public void init(MongoClient client, ClientEncryption clientEncryption) {
        this.DEKBase64 = createOrRetrieveDEK(clientEncryption);
        this.schemaMap = generateSchemaMap(DEKBase64);
        createPersonCollectionIfNecessary(client);
    }

    private void createPersonCollectionIfNecessary(MongoClient client) {
        MongoDatabase db = client.getDatabase(personNamespace.getDatabaseName());
        String collStr = personNamespace.getCollectionName();
        if (!doesPersonCollectionExist(db)) {
            db.createCollection(collStr, new CreateCollectionOptions().validationOptions(
                    new ValidationOptions().validator(getJsonSchemaWrapper())));
        }
    }

    private String createOrRetrieveDEK(ClientEncryption clientEncryption) {
        Base64.Encoder b64Encoder = Base64.getEncoder();
        BsonDocument personDEK = clientEncryption.getKeyByAltName(DEK_NAME);
        BsonBinary dataKeyId;
        if (personDEK == null) {
            LOGGER.info("=> Creating Data Encryption Key: " + DEK_NAME);
            DataKeyOptions dko = new DataKeyOptions().keyAltNames(of(DEK_NAME));
            dataKeyId = clientEncryption.createDataKey(KMS_PROVIDER, dko);
            LOGGER.debug("=> Person Collection DEK ID: " + dataKeyId);
        } else {
            LOGGER.info("=> Existing Data Encryption Key: " + DEK_NAME);
            dataKeyId = personDEK.get("_id").asBinary();
            LOGGER.debug("=> Person Collection DEK ID: " + dataKeyId);
        }
        LOGGER.debug("=> Person Collection Base64 DEK ID: " + b64Encoder.encodeToString(dataKeyId.getData()));
        return b64Encoder.encodeToString(dataKeyId.getData());
    }

    private boolean doesPersonCollectionExist(MongoDatabase db) {
        return db.listCollectionNames()
                 .into(new ArrayList<>())
                 .stream()
                 .anyMatch(c -> c.equals(personNamespace.getCollectionName()));
    }

    // TODO use the dynamic generation of the schema map using the Entity instead of this hardcoded version
    private BsonDocument generateSchemaMap(String dekId) {
        LOGGER.info("=> Generating Schema Map.");
        if (schemaMap != null) {
            LOGGER.info("=> Schema Map already exists. Returning existing value.");
            return schemaMap;
        }

        Document em = new Document().append("keyId", of(new Document().append("$binary",
                                                                              new Document().append("base64", dekId)
                                                                                            .append("subType", "04"))));
        Document ssn = new Document().append("encrypt", new Document().append("bsonType", "string")
                                                                      .append("algorithm",
                                                                              "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic"));
        Document bloodType = new Document().append("encrypt", new Document().append("bsonType", "string")
                                                                            .append("algorithm",
                                                                                    "AEAD_AES_256_CBC_HMAC_SHA_512-Random"));
        Document properties = new Document().append("ssn", ssn).append("bloodType", bloodType); // todo error when I changed from blood_type to bloodType
        Document schema = new Document().append("bsonType", "object")
                                        .append("encryptMetadata", em)
                                        .append("properties", properties);
        LOGGER.info("=> JSON Schema: " + schema.toJson(JsonWriterSettings.builder().indent(true).build()));
        return schemaMap = BsonDocument.parse(schema.toJson());
    }

    // TODO try to make this work but doesn't work without the MappingContext that is only created later by Spring with MongoDBSecureClientConfiguration
    /*private BsonDocument generateSchemaMap() {
        MongoJsonSchema personSchema = MongoJsonSchemaCreator.create() // todo error if I don't provide a context with EncryptionExtension for the SPEL evaluation from PersonEntity
                                                             .filter(MongoJsonSchemaCreator.encryptedOnly())
                                                             .createSchemaFor(PersonEntity.class);
        LOGGER.info("=======> JSON Schema: " + personSchema.schemaDocument().toJson());
        return personSchema.schemaDocument().toBsonDocument();
    }*/

    public BsonDocument getSchemaMap() {
        return schemaMap;
    }

    public BsonDocument getJsonSchemaWrapper() {
        return new BsonDocument("$jsonSchema", schemaMap);
    }

    public String getDEKBase64() {
        return DEKBase64;
    }

    public String getFullName() {
        return personNamespace.getFullName();
    }

}
