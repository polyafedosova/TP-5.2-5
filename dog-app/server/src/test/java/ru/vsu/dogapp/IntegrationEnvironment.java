package ru.vsu.dogapp;


import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

@Testcontainers
public abstract class IntegrationEnvironment {
    public static final JdbcDatabaseContainer<?> DB_CONTAINER;

    static {
        DB_CONTAINER = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("renty")
                .withUsername("user")
                .withPassword("password");
        DB_CONTAINER.start();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DB_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTAINER::getPassword);

        Startables.deepStart(DB_CONTAINER);
        //.thenAccept(unused -> runMigrations(DB_CONTAINER));
    }
}
