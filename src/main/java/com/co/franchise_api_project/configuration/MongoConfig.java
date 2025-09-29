package com.co.franchise_api_project.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, "mi_base");
    }

    /*@Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of());
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext,
            MongoCustomConversions conversions) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setCustomConversions(conversions);
        return converter;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(
            ReactiveMongoDatabaseFactory factory,
            MappingMongoConverter converter) {
        return new ReactiveMongoTemplate(factory, converter);
    } */
}

