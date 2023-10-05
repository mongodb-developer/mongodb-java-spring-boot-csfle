package com.mongodb.quickstart.javaspringbootcsfle.service;

public interface MasterKeyService {
    byte[] generateNewOrRetrieveMasterKeyFromFile();
}
