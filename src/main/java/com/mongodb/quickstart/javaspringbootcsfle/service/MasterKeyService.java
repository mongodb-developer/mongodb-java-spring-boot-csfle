package com.mongodb.quickstart.javaspringbootcsfle.services;


public interface MasterKeyService {
    byte[] generateNewOrRetrieveMasterKeyFromFile();
}
