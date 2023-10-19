package com.mongodb.quickstart.javaspringbootcsfle.csfleService;

import java.util.Map;

public interface KmsService {
    Map<String, Map<String, Object>> getKmsProviders();
}
