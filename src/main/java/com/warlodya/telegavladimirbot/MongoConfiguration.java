package com.warlodya.telegavladimirbot;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @NotNull
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(new ConnectionString("mongodb://mongodb:27017"));
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        return "bot_database";
    }

    @NotNull
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
