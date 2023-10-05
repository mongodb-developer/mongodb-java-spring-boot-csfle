package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.exists;

@Component
public class KeyVaultCollectionSetup {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyVaultCollectionSetup.class);
    private static final String INDEX_NAME = "uniqueKeyAltNames";
    private final MongoClient mongoClient;
    @Value("${mongodb.key.vault.db}")
    private String KEY_VAULT_DB;
    @Value("${mongodb.key.vault.coll}")
    private String KEY_VAULT_COLL;

    public KeyVaultCollectionSetup(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("=> Checking the vault collection.");
        MongoDatabase db = mongoClient.getDatabase(KEY_VAULT_DB);
        MongoCollection<Document> vault = db.getCollection(KEY_VAULT_COLL);
        boolean vaultExists = doesCollectionExist(db, KEY_VAULT_COLL);
        if (vaultExists) {
            LOGGER.info("=> Vault collection already exists.");
            boolean indexExists = doesIndexExist(vault, INDEX_NAME);
            if (!indexExists) {
                LOGGER.info("=> Unique index created on the keyAltNames");
                createKeyVaultIndex(vault);
            }
        } else {
            LOGGER.info("=> Creating a new vault collection + index on keyAltNames.");
            createKeyVaultIndex(vault);
        }
    }

    private void createKeyVaultIndex(MongoCollection<Document> vault) {
        Bson keyAltNamesExists = exists("keyAltNames");
        IndexOptions indexOpts = new IndexOptions().name(INDEX_NAME).partialFilterExpression(keyAltNamesExists).unique(true);
        vault.createIndex(new BsonDocument("keyAltNames", new BsonInt32(1)), indexOpts);
    }

    private boolean doesIndexExist(MongoCollection<Document> coll, String indexName) {
        return coll.listIndexes()
                   .into(new ArrayList<>())
                   .stream()
                   .map(i -> i.get("name"))
                   .anyMatch(n -> n.equals(indexName));
    }

    private boolean doesCollectionExist(MongoDatabase db, String coll) {
        return db.listCollectionNames().into(new ArrayList<>()).stream().anyMatch(c -> c.equals(coll));
    }
}
