package com.targa.labs.dev.rest;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class DatabaseTestResource implements QuarkusTestResourceLifecycleManager {
    private static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>("postgres:13");

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        Map<String, String> map = new HashMap<>();
        map.put("quarkus.datasource.reactive.url",
                String.format("postgresql://%s:%d/%s",
                        DATABASE.getHost(),
                        DATABASE.getFirstMappedPort(),
                        DATABASE.getDatabaseName()
                )
        );
        map.put("quarkus.datasource.username", DATABASE.getUsername());
        map.put("quarkus.datasource.password", DATABASE.getPassword());
        return map;
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}