package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.client.MongoClient;
import com.mongodb.client.vault.ClientEncryption;
import org.bson.BsonDocument;

public interface EncryptedCollection {

    void init(MongoClient client, ClientEncryption clientEncryption);

    BsonDocument getSchemaMap();

    BsonDocument getJsonSchemaWrapper();

    String getFullName();

}
