/*
package com.mongodb.quickstart.javaspringbootcsfle.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoClient;
import com.mongodb.quickstart.javaspringbootcsfle.components.PersonCollectionSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoManagedTypes;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

*/
/*
 * Configuration for the standard MongoClient Bean.
 * MongoClient that we use to ensure that the unique index exists on the key vault collection.
 * This class also creates the MongoTemplate bean.
*//*


@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@EnableMongoRepositories()
public class MongoDBStandardClientConfiguration extends AbstractMongoClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBStandardClientConfiguration.class);
    @Value("${spring.data.mongodb.storage.uri}")
    private String CONNECTION_STR;

*/
/*
     * Connection database.
     *
     * @return The database for the "persons" collection.
*//*


    @Override
    protected String getDatabaseName() {
        return PersonCollectionSetup.getNamespace().getDatabaseName();
    }

*/
/*
     * Configuration for the standard MongoClient Bean.
     *
     * @param builder never {@literal null}.
*//*


    @Override
    protected void configureClientSettings(Builder builder) {
        builder.applyConnectionString(new ConnectionString(CONNECTION_STR));
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return super.mongoClient();
    }

*/
/*    @Override
    @Bean(name = "mongoStandardMappingContext")
    public MongoMappingContext mongoMappingContext(MongoCustomConversions customConversions, MongoManagedTypes mongoManagedTypes) {
        return super.mongoMappingContext(customConversions, mongoManagedTypes);
    }*//*




    @Override
    @Bean(name = "mongoTemplateStandard")
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactoryStandard") MongoDatabaseFactory databaseFactory,
                                       @Qualifier("mappingMongoConverterStandard") MappingMongoConverter converter) {
        return super.mongoTemplate(databaseFactory, converter);
    }

    @Override
    @Primary
    @Bean(name = "mongoDbFactoryStandard")
    public MongoDatabaseFactory mongoDbFactory() {
        return super.mongoDbFactory();
    }

    @Override
    @Primary
    @Bean(name = "mappingMongoConverterStandard")
    public MappingMongoConverter mappingMongoConverter(@Qualifier("mongoDbFactoryStandard") MongoDatabaseFactory databaseFactory,
                                                       @Qualifier("customConversionsStandard") MongoCustomConversions customConversions,
                                                       @Qualifier("mongoMappingContextStandard") MongoMappingContext mappingContext) {
        return super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
    }

    @Override
    @Primary
    @Bean(name = "mongoMappingContextStandard")
    public MongoMappingContext mongoMappingContext(@Qualifier("customConversionsStandard") MongoCustomConversions customConversions,
                                                   @Qualifier("mongoManagedTypesStandard") MongoManagedTypes mongoManagedTypes) {
        return super.mongoMappingContext(customConversions, mongoManagedTypes);
    }

    @Override
    @Bean(name = "customConversionsStandard")
    public MongoCustomConversions customConversions() {
        return super.customConversions();
    }

    @Override
    @Bean("mongoManagedTypesStandard")
    public MongoManagedTypes mongoManagedTypes() throws ClassNotFoundException {
        return super.mongoManagedTypes();
    }
}

*/
