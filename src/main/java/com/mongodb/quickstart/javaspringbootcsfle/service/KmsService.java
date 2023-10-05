package com.mongodb.quickstart.javaspringbootcsfle.service;

import java.util.Map;

public interface KmsService {
    Map<String, Map<String, Object>> getKmsProviders();
}
