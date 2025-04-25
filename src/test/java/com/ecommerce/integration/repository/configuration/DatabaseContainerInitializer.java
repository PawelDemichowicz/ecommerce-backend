package com.ecommerce.integration.repository.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseContainerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String POSTGRESQL_USERNAME = "test";
    public static final String POSTGRESQL_PASSWORD = "test";
    public static final String POSTGRESQL_BEAN_NAME = "postgres";
    public static final String POSTGRESQL_CONTAINER = "postgres:17.2";

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(POSTGRESQL_CONTAINER)
                .withUsername(POSTGRESQL_USERNAME)
                .withPassword(POSTGRESQL_PASSWORD);
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());

        applicationContext.addApplicationListener(event -> {
            if (event instanceof ContextClosedEvent) {
                container.close();
            }
        });

        applicationContext.getBeanFactory().registerSingleton(POSTGRESQL_BEAN_NAME, container);
    }
}
