package com.targa.labs.dev.util;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class FlywayMigrationService {

    @ConfigProperty(name = "quarkus.datasource.reactive.url")
    String dbUrl;
    @ConfigProperty(name = "quarkus.datasource.username")
    String dbUsername;
    @ConfigProperty(name = "quarkus.datasource.password")
    String dbPassword;

    public void runFlywayMigration(@Observes StartupEvent event) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:" + dbUrl, dbUsername, dbPassword)
                .load();

        flyway.migrate();
    }
}
