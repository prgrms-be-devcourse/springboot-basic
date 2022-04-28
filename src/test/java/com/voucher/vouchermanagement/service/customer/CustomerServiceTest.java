package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.configuration.YamlPropertiesFactory;
import com.voucher.vouchermanagement.repository.customer.CustomerJdbcRepository;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {

    @Autowired
    private CustomerService voucherService;

    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = {"com.voucher.vouchermanagement.service.customer",
            "com.voucher.vouchermanagement.repository.customer"})
    @PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
    static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }

        @Bean
        PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    public void joinTest() {

    }

    @Test
    public void multiJoinTest() {

    }

    @Test
    public void findByIdTest() {

    }
    @Test
    public void findByNameTest() {

    }
    @Test
    public void findByEmailTest() {

    }

    @Test
    public void findAllTest() {

    }

    @Test
    public void deleteAllTest() {

    }
}