package com.mongodb.quickstart.javaspringbootcsfle.components;/*
package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.client.vault.ClientEncryption;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.List.of;

@Component
public class EncryptedCollectionsSetup {

    private final List<EncryptedCollection> encryptedCollections;

    public EncryptedCollectionsSetup(PersonCollectionSetup personCollectionSetup) {
        this.encryptedCollections = of(personCollectionSetup);
    }

    public List<EncryptedCollection> init(ClientEncryption clientEncryption) {
        encryptedCollections.forEach(coll -> coll.init(clientEncryption));
        return encryptedCollections;
    }
}
*/

import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.SchemaService;
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
//@DependsOn("mongoDBSecureClientConfiguration") // todo clean
public class EncryptedCollectionsSetup {

    private final MongoClient mongoClient;
    private final SchemaService schemaService;

    public EncryptedCollectionsSetup(MongoClient mongoClient, SchemaService schemaService) {
        this.mongoClient = mongoClient;
        this.schemaService = schemaService;
    }

    @PostConstruct
    public void postConstruct() {
        schemaService.getSchemasMap()
                     .forEach((namespace, schema) -> createCollectionIfNecessary(mongoClient, namespace, schema));
    }

    private void createCollectionIfNecessary(MongoClient mongoClient, MongoNamespace ns, BsonDocument schema) {
        MongoDatabase db = mongoClient.getDatabase(ns.getDatabaseName());
        String collStr = ns.getCollectionName();
        if (!doesCollectionExist(db, ns)) {
            db.createCollection(collStr, new CreateCollectionOptions().validationOptions(
                    new ValidationOptions().validator(jsonSchemaWrapper(schema))));
        }
    }

    public BsonDocument jsonSchemaWrapper(BsonDocument schema) {
        return new BsonDocument("$jsonSchema", schema);
    }

    private boolean doesCollectionExist(MongoDatabase db, MongoNamespace ns) {
        return db.listCollectionNames()
                 .into(new ArrayList<>())
                 .stream()
                 .anyMatch(c -> c.equals(ns.getCollectionName()));
    }

}