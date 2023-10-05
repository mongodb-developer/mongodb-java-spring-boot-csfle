package com.mongodb.quickstart.javaspringbootcsfle.serviceImpl;

import com.mongodb.quickstart.javaspringbootcsfle.service.KmsService;
import com.mongodb.quickstart.javaspringbootcsfle.service.MasterKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Do not use a local file to store your Master Key in production.
 * Use a proper KMS provider instead.
 * <a href="https://www.mongodb.com/docs/manual/core/csfle/reference/kms-providers/#supported-key-management-services">MongoDB KMS provider documentation</a>
 */
@Service
public class KmsServiceImpl implements KmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KmsServiceImpl.class);
    private final MasterKeyService masterKeyService;
    @Value("${mongodb.kms.provider}")
    private String LOCAL;

    public KmsServiceImpl(MasterKeyService masterKeyService) {
        this.masterKeyService = masterKeyService;
    }

    public Map<String, Map<String, Object>> getKmsProviders() {
        LOGGER.info("=> Creating local Key Management System using the master key.");
        return new HashMap<>() {{
            put(LOCAL, new HashMap<>() {{
                put("key", masterKeyService.generateNewOrRetrieveMasterKeyFromFile());
            }});
        }};
    }

}
