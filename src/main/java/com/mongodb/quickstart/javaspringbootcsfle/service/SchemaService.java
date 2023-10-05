package com.mongodb.quickstart.javaspringbootcsfle.service;

import org.bson.BsonDocument;

import java.util.Map;

public interface SchemaService {
    Map<String, BsonDocument> getSecureClientSchemaMap();
}
