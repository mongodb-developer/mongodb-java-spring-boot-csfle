package com.mongodb.quickstart.javaspringbootcsfle;

import com.mongodb.AutoEncryptionSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.quickstart.javaspringbootcsfle.services.KmsService;
import com.mongodb.quickstart.javaspringbootcsfle.services.SchemaService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MongoDBSecureClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBSecureClientConfiguration.class);
    private final KmsService kmsService;
    private final SchemaService schemaService;
    @Value("${crypt.shared.lib.path}")
    private String CRYPT_SHARED_LIB_PATH;
    @Value("${spring.data.mongodb.uri}")
    private String CONNECTION_STR;
    @Value("${mongodb.key.vault.db}")
    private String KEY_VAULT_DB;
    @Value("${mongodb.key.vault.coll}")
    private String KEY_VAULT_COLL;
    private MongoNamespace KEY_VAULT_NS;

    public MongoDBSecureClientConfiguration(KmsService kmsService, SchemaService schemaService) {
        this.kmsService = kmsService;
        this.schemaService = schemaService;
    }

    @PostConstruct
    public void postConstruct() {
        this.KEY_VAULT_NS = new MongoNamespace(KEY_VAULT_DB, KEY_VAULT_COLL);
    }

    @Bean
    public MongoClient mongoClient() {
        LOGGER.info("=> Creating MongoDB client with automatic decryption.");
        Map<String, Object> extraOptions = Map.of("cryptSharedLibPath", CRYPT_SHARED_LIB_PATH, "cryptSharedLibRequired", true);
        AutoEncryptionSettings aes = AutoEncryptionSettings.builder()
                                                           .keyVaultNamespace(KEY_VAULT_NS.getFullName())
                                                           .kmsProviders(kmsService.getKmsProviders())
                                                           .schemaMap(schemaService.getSecureClientSchemaMap())
                                                           .extraOptions(extraOptions)
                                                           .build();
        MongoClientSettings mcs = MongoClientSettings.builder()
                                                     .applyConnectionString(new ConnectionString(CONNECTION_STR))
                                                     .autoEncryptionSettings(aes)
                                                     .build();
        return MongoClients.create(mcs);
    }
}
