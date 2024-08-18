package io.tracksystem.persistence.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

// @Configuration
@EnableMongoRepositories(basePackages = "io.tracksystem.persistence.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongo_uri;

    @Override
    protected String getDatabaseName() {
        return System.getenv().getOrDefault("SPRING_DATA_MONGODB_DATABASE", "trackingsystem");
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(System.getenv("SPRING_DATA_MONGODB_URI"));
    }
}
