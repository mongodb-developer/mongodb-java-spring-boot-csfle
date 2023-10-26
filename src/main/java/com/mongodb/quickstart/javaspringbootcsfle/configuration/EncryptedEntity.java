package com.mongodb.quickstart.javaspringbootcsfle.configuration;

import com.mongodb.MongoNamespace;

/**
 * This class contains the metadata about the encrypted collections.
 */
public class EncryptedEntity {
    public MongoNamespace namespace;
    public Class<?> entityClass;
    public String dekName;

    public EncryptedEntity(String database,
                           String collection,
                           Class<?> entityClass,
                           String dekName) {
        this.namespace = new MongoNamespace(database, collection);
        this.entityClass = entityClass;
        this.dekName = dekName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getDekName() {
        return dekName;
    }

    public MongoNamespace getNamespace() {
        return namespace;
    }
}
