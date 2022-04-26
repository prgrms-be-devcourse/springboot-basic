package org.prgrms.kdtspringdemo.testcontainers;


import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerJdbcRepository;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherJdbcRepository;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@Testcontainers
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig
public abstract class AbstractContainerDatabaseTest {
    @Container
    private static final MySQLContainer mysqlContainer = (MySQLContainer)
            new MySQLContainer("mysql:8.0.19")
                    .withInitScript("schema.sql");

    @Configuration
    @ComponentScan(basePackages = "org.prgrms.kdtspringdemo.domain")
    static class Config {

        @Bean
        public DataSource dataSource() {
            mysqlContainer.start();

            return DataSourceBuilder.create()
                    .driverClassName(mysqlContainer.getDriverClassName())
                    .url(mysqlContainer.getJdbcUrl())
                    .username(mysqlContainer.getUsername())
                    .password(mysqlContainer.getPassword())
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public CustomerRepository customerRepository(
                NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public VoucherRepository voucherRepository(
                NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new VoucherJdbcRepository(namedParameterJdbcTemplate);
        }
    }
}
