package com.mongodb.quickstart.javaspringbootcsfle.csfleService;

import com.mongodb.client.MongoClient;

public interface KeyVaultService {
    void setupKeyVaultCollection(MongoClient mongoClient);
}
