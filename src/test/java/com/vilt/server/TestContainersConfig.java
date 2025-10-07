package com.vilt.server;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration
class TestContainersConfig {

    @Bean
    MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:6");
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar(MongoDBContainer mongoDbContainer) {
        return registry -> {
            registry.add("spring.data.mongodb.uri", mongoDbContainer::getConnectionString);
            registry.add("spring.data.mongodb.database", () -> "server-db");
        };
    }

}
