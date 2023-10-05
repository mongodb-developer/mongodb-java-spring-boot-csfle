package com.mongodb.quickstart.javaspringbootcsfle.services;

import org.bson.BsonDocument;

import java.util.Map;

public interface SchemaService {
    Map<String, BsonDocument> getSecureClientSchemaMap();
}
