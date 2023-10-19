package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.MongoNamespace;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;

import java.util.List;
import java.util.Map;

public class EncryptedCollectionsComponent {

    // todo clean up useless stuff
    public static final List<String> collectionDEKNames = List.of("PersonEntity"); // todo I should be able to put the name I want. Not the default name of the entity
    public static final Map<MongoNamespace, Class<PersonEntity>> collectionMap = Map.of(
            new MongoNamespace("test", "persons"), PersonEntity.class);

}
