package org.prgrms.springbasic.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.springbasic.repository.customer.CustomerJdbcRepository;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.repository.voucher.VoucherJdbcRepository;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@DirtiesContext
@SpringJUnitConfig
public abstract class TestDBContainer {

    @Container
    private static final MySQLContainer mysqlContainer = (MySQLContainer)
            new MySQLContainer("mysql:8.0.26")
                    .withInitScript("data.sql");

    @Configuration
    @ComponentScan(basePackages = "org.prgrms.springbasic")
    static class Config {
        @Bean
        public DataSource dataSource() {
            mysqlContainer.withInitScript("data.sql").start();

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
        public CustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public VoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new VoucherJdbcRepository(namedParameterJdbcTemplate);
        }
    }
}


