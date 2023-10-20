package com.mongodb.quickstart.javaspringbootcsfle.csfleService;

import com.mongodb.quickstart.javaspringbootcsfle.configuration.EncryptedEntity;

import java.util.Map;

public interface DataEncryptionKeyService {
    String createOrRetrieveDEK(EncryptedEntity encryptedEntity);

    Map<String, String> getDataEncryptionKeysB64();
}
