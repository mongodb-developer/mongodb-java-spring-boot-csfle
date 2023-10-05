package com.mongodb.quickstart.javaspringbootcsfle.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoClient;
import com.mongodb.quickstart.javaspringbootcsfle.components.PersonCollectionSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * Configuration for the standard MongoClient Bean.
 * MongoClient that we use to ensure that the unique index exists on the key vault collection.
 * This class also creates the MongoTemplate bean.
 */
@Configuration
public class MongoDBStandardClientConfiguration extends AbstractMongoClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBStandardClientConfiguration.class);
    @Value("${spring.data.mongodb.storage.uri}")
    private String CONNECTION_STR;

    /**
     * Connection database.
     *
     * @return The database for the "persons" collection.
     */
    @Override
    protected String getDatabaseName() {
        return PersonCollectionSetup.getPersonNamespace().getDatabaseName();
    }

    /**
     * Configuration for the standard MongoClient Bean.
     *
     * @param builder never {@literal null}.
     */
    @Override
    protected void configureClientSettings(Builder builder) {
        builder.applyConnectionString(new ConnectionString(CONNECTION_STR));
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return super.mongoClient();
    }
}
