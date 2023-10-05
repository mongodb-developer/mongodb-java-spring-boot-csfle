package com.mongodb.quickstart.javaspringbootcsfle.services;

import java.util.Map;

public interface KmsService {
    Map<String, Map<String, Object>> getKmsProviders();
}
