package com.mongodb.quickstart.javaspringbootcsfle.serviceImpl;

import com.mongodb.quickstart.javaspringbootcsfle.components.EncryptedCollection;
import com.mongodb.quickstart.javaspringbootcsfle.components.EncryptionSetup;
import com.mongodb.quickstart.javaspringbootcsfle.service.SchemaService;
import org.bson.BsonDocument;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
public class SchemaServiceImpl implements SchemaService {

    private final EncryptionSetup encryptionSetup;

    public SchemaServiceImpl(EncryptionSetup encryptionSetup) {
        this.encryptionSetup = encryptionSetup;
    }

    public Map<String, BsonDocument> getSecureClientSchemaMap() {
        return encryptionSetup.getEncryptedCollections()
                              .stream()
                              .collect(toMap(EncryptedCollection::getFullName, EncryptedCollection::getSchemaMap));
    }

}
