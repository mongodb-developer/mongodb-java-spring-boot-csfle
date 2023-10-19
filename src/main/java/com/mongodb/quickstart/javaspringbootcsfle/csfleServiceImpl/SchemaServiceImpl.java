package com.mongodb.quickstart.javaspringbootcsfle.csfleServiceImpl;

import com.mongodb.MongoNamespace;
import com.mongodb.quickstart.javaspringbootcsfle.components.EncryptedCollectionsComponent;
import com.mongodb.quickstart.javaspringbootcsfle.components.EncryptionSetup;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.SchemaService;
import com.mongodb.quickstart.javaspringbootcsfle.model.EncryptedEntity;
import org.bson.BsonDocument;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
public class SchemaServiceImpl implements SchemaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaServiceImpl.class);
    private final EncryptionSetup encryptionSetup;
    private Map<MongoNamespace, BsonDocument> schemasMap;

    public SchemaServiceImpl(EncryptionSetup encryptionSetup) {
        this.encryptionSetup = encryptionSetup;
    }

    @Override
    public Map<MongoNamespace, BsonDocument> generateSchemasMap(MongoJsonSchemaCreator schemaCreator) {
        LOGGER.info("=> Generating schema map.");

        // TODO clean up this code

        //        schemaMap = new HashMap<>();
//        schemaMap.put(PersonEntity.getFullName(), generateSchema(schemaCreator, PersonEntity.class));

        /*List<Class<? extends EncryptedEntity>> entitiesClass = List.of(PersonEntity.class);
        return this.schemaMap = entitiesClass.stream().collect(toMap(e -> ((EncryptedEntity) e).getClass().getFullName(),
                                                                          e -> generateSchema(schemaCreator,
                                                                                              e.getEntityClass())));*/
/*
        schemaMap = EncryptedCollectionsComponent.collectionMap.entrySet()
                                                   .stream()
                                                   .map(e -> Map.entry(e.getKey(),
                                                                       generateSchema(schemaCreator, e.getValue())))
                                                   .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
*/
        return schemasMap = EncryptedCollectionsComponent.collectionMap.entrySet()
                                                                       .stream()
                                                                       .collect(toMap(Map.Entry::getKey,
                                                                                      e -> generateSchema(schemaCreator,
                                                                                                          e.getValue())));
    }

    @Override
    public Map<MongoNamespace, BsonDocument> getSchemasMap() {
        return schemasMap;
    }

    private BsonDocument generateSchema(MongoJsonSchemaCreator schemaCreator, Class<? extends EncryptedEntity> entityClass) {
        BsonDocument schema = schemaCreator.filter(MongoJsonSchemaCreator.encryptedOnly())
                                           .createSchemaFor(entityClass)
                                           .schemaDocument()
                                           .toBsonDocument();
        LOGGER.info("=> JSON Schema: " + schema.toJson(JsonWriterSettings.builder().indent(true).build()));
        return schema;
    }

}
