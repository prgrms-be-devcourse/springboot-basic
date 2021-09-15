package com.programmers.kdtspringorder.customer;

import com.programmers.kdtspringorder.customer.service.CustomerService;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.kdtspringorder"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("user")
                    .password("user")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);

            System.out.println("dataSource = " + dataSource);

            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    CustomerService customerService;


}