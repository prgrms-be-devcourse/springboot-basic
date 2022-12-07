package com.programmers.voucher;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MysqlTestContainer {

    private static final Logger logger = LoggerFactory.getLogger(MysqlTestContainer.class);

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.24")
            .withInitScript("schema.sql");

    @BeforeAll
    public static void before() {
        logger.info(MY_SQL_CONTAINER.getJdbcUrl());
    }
}
