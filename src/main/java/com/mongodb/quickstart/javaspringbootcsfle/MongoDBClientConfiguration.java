package com.mongodb.quickstart.javaspringbootcsfle;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClientConfiguration.class);
    @Value("${spring.data.mongodb.uri}")
    private String CONNECTION_STR;

    /**
     * MongoDB client that we use to ensure that the unique index exist on the key vault collection.
     *
     * @return mongoClient standard connection to MongoDB without any encryption.
     */
    @Bean(name = "standardMongoClient")
    public MongoClient mongoClient() {
        LOGGER.info("=> Standard MongoDB Connection created!");
        MongoClientSettings mcs = MongoClientSettings.builder()
                                                     .applyConnectionString(new ConnectionString(CONNECTION_STR))
                                                     .build();
        return MongoClients.create(mcs);
    }
}
