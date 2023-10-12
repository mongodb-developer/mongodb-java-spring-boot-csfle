/*
package com.mongodb.quickstart.javaspringbootcsfle.ccschema;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

//@DependsOn("keyVaultCollectionSetup") // TODO
@Component
public class PersonSchemaComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonSchemaComponent.class);
    private BsonDocument schemaMap;

    public PersonSchemaComponent() {
    }

*/
/*
    @PostConstruct
    public void postConstruct() {
        this.schemaMap = generateSchemaMap();
    }
*//*



*/
/*    private BsonDocument generateSchemaMap() {
        MongoJsonSchema personSchema = MongoJsonSchemaCreator.create()
                                                             .filter(MongoJsonSchemaCreator.encryptedOnly())
                                                             .createSchemaFor(PersonEntity.class);
        LOGGER.info("=======> JSON Schema: " + personSchema.schemaDocument().toJson());
        return personSchema.schemaDocument().toBsonDocument();
    }*//*


    // TODO use the dynamic generation of the schema map using the Entity instead of this hardcoded version
    private BsonDocument generateSchemaMap(String dekId) {
        if (schemaMap != null) {
            return schemaMap;
        }

        Document jsonSchema = new Document().append("bsonType", "object")
                                            .append("encryptMetadata", new Document().append("keyId",
                                                                                             List.of(new Document().append(
                                                                                                     "$binary",
                                                                                                     new Document().append(
                                                                                                                           "base64",
                                                                                                                           dekId)
                                                                                                                   .append("subType",
                                                                                                                           "04")))))
                                            .append("properties", new Document().append("ssn",
                                                                                        new Document().append("encrypt",
                                                                                                              new Document().append(
                                                                                                                                    "bsonType",
                                                                                                                                    "string")
                                                                                                                            .append("algorithm",
                                                                                                                                    "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic")))
                                                                                .append("blood_type",
                                                                                        new Document().append("encrypt",
                                                                                                              new Document().append(
                                                                                                                                    "bsonType",
                                                                                                                                    "string")
                                                                                                                            .append("algorithm",
                                                                                                                                    "AEAD_AES_256_CBC_HMAC_SHA_512-Random"))));
        LOGGER.info("=> JSON Schema: " + jsonSchema.toJson(JsonWriterSettings.builder().indent(true).build()));
        return schemaMap = BsonDocument.parse(jsonSchema.toJson());
    }


    public BsonDocument getSchemaMap() {
        return schemaMap;
    }

    public BsonDocument getJsonSchemaWrapper() {
        return new BsonDocument("$jsonSchema", schemaMap);
    }
}
*/
