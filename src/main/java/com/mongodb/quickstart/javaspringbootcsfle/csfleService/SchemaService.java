package com.mongodb.quickstart.javaspringbootcsfle.csfleService;

import com.mongodb.MongoNamespace;
import org.bson.BsonDocument;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;

import java.util.Map;

public interface SchemaService {
    Map<MongoNamespace, BsonDocument> generateSchemasMap(MongoJsonSchemaCreator schemaCreator);
    Map<MongoNamespace, BsonDocument> getSchemasMap();
}
