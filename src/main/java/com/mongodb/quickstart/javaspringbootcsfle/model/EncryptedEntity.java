package com.mongodb.quickstart.javaspringbootcsfle.model;

import com.mongodb.MongoNamespace;

// todo remove entirely?
public abstract class EncryptedEntity {
    public static MongoNamespace NAMESPACE;
    public static Class<? extends EncryptedEntity> ENTITY_CLASS;
    public static String DEK_NAME;

    public static String getFullName() {
        return NAMESPACE.getFullName();
    }

    public static Class<? extends EncryptedEntity> getEntityClass() {
        return ENTITY_CLASS;
    }

    public static String getDekName() {
        return DEK_NAME;
    }

    public static MongoNamespace getNamespace() {
        return NAMESPACE;
    }
}
