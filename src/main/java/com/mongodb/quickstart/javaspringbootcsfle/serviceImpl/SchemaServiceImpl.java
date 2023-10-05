package com.mongodb.quickstart.javaspringbootcsfle.serviceImpl;

import com.mongodb.quickstart.javaspringbootcsfle.components.PersonCollectionSetup;
import com.mongodb.quickstart.javaspringbootcsfle.service.SchemaService;
import org.bson.BsonDocument;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * The Schema Service class builds the JsonSchema for the MongoDB Secure Client by collecting the schema from all the encrypted collections.
 * In this quickstart there is only one Person collection.
 */
@Service
public class SchemaServiceImpl implements SchemaService {

    private final PersonCollectionSetup personCollectionSetup;

    public SchemaServiceImpl(PersonCollectionSetup personCollectionSetup) {
        this.personCollectionSetup = personCollectionSetup;
    }

    public Map<String, BsonDocument> getSecureClientSchemaMap() {
        return Map.of(PersonCollectionSetup.getPersonNamespace().getFullName(), personCollectionSetup.getSchemaMap());
    }

}
