package com.mongodb.quickstart.javaspringbootcsfle.components;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.vault.ClientEncryption;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.KeyVaultService;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.DataEncryptionKeyService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EncryptionSetup {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionSetup.class);
    private final KeyVaultService keyVaultService;
    private final DataEncryptionKeyService dataEncryptionKeyService;
//    private List<EncryptedCollection> encryptedCollections;
//    private EncryptedCollectionsSetup encryptedCollectionsSetup;
    @Value("${spring.data.mongodb.storage.uri}")
    private String CONNECTION_STR;

    public EncryptionSetup(KeyVaultService keyVaultService,
                           DataEncryptionKeyService dataEncryptionKeyService) {
        this.keyVaultService = keyVaultService;
        this.dataEncryptionKeyService = dataEncryptionKeyService;
//        this.encryptedCollectionsSetup = encryptedCollectionsSetup;
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("=> Start Encryption Setup.");
        LOGGER.debug("=> CONNECTION_STR: " + CONNECTION_STR);
        MongoClientSettings mcs = MongoClientSettings.builder()
                                                     .applyConnectionString(new ConnectionString(CONNECTION_STR))
                                                     .build();
        try (MongoClient client = MongoClients.create(mcs)) {
            LOGGER.info("=> Created the MongoClient instance for the encryption setup.");
            LOGGER.info("=> Creating the encryption key vault collection.");
            keyVaultService.setupKeyVaultCollection(client);
            LOGGER.info("=> Creating the Data Encryption Keys.");
            List<String> dekNameList = EncryptedCollectionsComponent.collectionDEKNames;
            dekNameList.forEach(dataEncryptionKeyService::createOrRetrieveDEK);
//            this.encryptedCollections = encryptedCollectionsSetup.init(clientEncryption);
            LOGGER.info("=> Encryption Setup completed.");
        } catch (Exception e) {
            LOGGER.error("=> Encryption Setup failed: ", e.getMessage(), e);
        }

    }

    /*public List<EncryptedCollection> getEncryptedCollections() {
        return encryptedCollections;
    }*/
}
