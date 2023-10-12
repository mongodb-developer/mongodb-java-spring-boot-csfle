package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.client.MongoClient;
import com.mongodb.client.vault.ClientEncryption;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.List.of;

@Component
public class EncryptedCollectionsSetup {

    private final List<EncryptedCollection> encryptedCollections;

    public EncryptedCollectionsSetup(PersonCollectionSetup personCollectionSetup) {
        this.encryptedCollections = of(personCollectionSetup);
    }

    public List<EncryptedCollection> init(MongoClient client, ClientEncryption clientEncryption) {
        encryptedCollections.forEach(coll -> coll.init(client, clientEncryption));
        return encryptedCollections;
    }
}
