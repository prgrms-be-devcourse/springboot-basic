package com.programmers.vouchermanagement.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class ContainerBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> MY_SQL_CONTAINER.getDriverClassName());
        registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
    }
}
