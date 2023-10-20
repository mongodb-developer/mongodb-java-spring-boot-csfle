package com.mongodb.quickstart.javaspringbootcsfle.configuration;

import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;

import java.util.List;

public class EncryptedCollectionsConfiguration {
    public static final List<EncryptedEntity> encryptedEntities = List.of(
            new EncryptedEntity("mydb", "persons", PersonEntity.class, "personDEK"));
}
