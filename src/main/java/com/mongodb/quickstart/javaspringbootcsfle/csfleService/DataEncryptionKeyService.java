package com.mongodb.quickstart.javaspringbootcsfle.csfleService;

import java.util.Map;

public interface DataEncryptionKeyService {
    String createOrRetrieveDEK(String dekName);

    Map<String, String> getDataEncryptionKeysB64();
}
