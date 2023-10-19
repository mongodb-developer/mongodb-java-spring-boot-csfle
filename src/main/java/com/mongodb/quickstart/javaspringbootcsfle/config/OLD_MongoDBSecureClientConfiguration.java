/*
package com.mongodb.quickstart.javaspringbootcsfle.config;

import com.mongodb.AutoEncryptionSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoClient;
import com.mongodb.lang.NonNull;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.KmsService;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.SchemaService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
public class MongoDBSecureClientConfiguration extends AbstractMongoClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBSecureClientConfiguration.class);
    private final KmsService kmsService;
    private final SchemaService schemaService;
    @Value("${crypt.shared.lib.path}")
    private String CRYPT_SHARED_LIB_PATH;
    @Value("${spring.data.mongodb.storage.uri}")
    private String CONNECTION_STR;
    @Value("${mongodb.default.db}")
    private String DEFAULT_DB;
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

    */
/**
     * Configuration for the secured MongoClient Bean.
     *
     * @param builder never {@literal null}.
     *//*

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        Map<String, Object> extraOptions = Map.of("cryptSharedLibPath", CRYPT_SHARED_LIB_PATH, "cryptSharedLibRequired",
                                                  true);
        AutoEncryptionSettings aes = AutoEncryptionSettings.builder()
                                                           .keyVaultNamespace(KEY_VAULT_NS.getFullName())
                                                           .kmsProviders(kmsService.getKmsProviders())
                                                           .schemaMap(schemaService.getSecureClientSchemaMap())
                                                           .extraOptions(extraOptions)
                                                           .build();
        builder.applyConnectionString(new ConnectionString(CONNECTION_STR)).autoEncryptionSettings(aes).build();
    }

    */
/**
     * This client is configured to automatically encrypt and decrypt the data.
     *
     * @return MongoClient
     *//*

    @Override
    @NonNull
    public MongoClient mongoClient() {
        LOGGER.info("=> Creating MongoDB client with automatic encryption/decryption.");
        return super.mongoClient();
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return DEFAULT_DB;
    }

    @Override
    @NonNull
    protected Collection<String> getMappingBasePackages() {
        return List.of("com.mongodb.quickstart.javaspringbootcsfle.model");
    }

    */
/**
     * Triggers the automatic creation of the indexes of the fields in the @Document annotated classes with @Indexed annotations.
     *
     * @return true
     *//*

    @Override
    public boolean autoIndexCreation() {
        return true;
    }
}
*/
