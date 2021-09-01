package com.example.kdtspringmission.customer.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.Charset.UTF8;


import com.example.kdtspringmission.customer.domain.Customer;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
        basePackages = {"com.example.kdtspringmission.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;
    List<Customer> dummyCustomers;

    @BeforeAll
    void beforeAll() {
        setUpEmbeddedMysql();
        setUpDummyData();
    }

    @BeforeEach
    void beforeEach() {
        customerJdbcRepository.deleteAll();
    }

    @AfterAll
    void afterAll() {
        embeddedMysql.stop();
    }

    private void setUpEmbeddedMysql() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", classPathScript("schema.sql"))
            .start();
    }

    private void setUpDummyData() {
        dummyCustomers = new ArrayList<>() {{
            add(new Customer(UUID.randomUUID(), "tester1", "tester1@test.com",
                LocalDateTime.now()));
            add(new Customer(UUID.randomUUID(), "tester2", "tester2@test.com",
                LocalDateTime.now()));
            add(new Customer(UUID.randomUUID(), "tester3", "tester3@test.com",
                LocalDateTime.now()));
        }};
    }

    @Test
    @DisplayName("데이터 추가")
    void testInsert() {
        for (Customer customer : dummyCustomers) {
            Customer insertedCustomer = customerJdbcRepository.insert(customer);
            assertThat(insertedCustomer).isEqualTo(customer);
        }
    }

    @Test
    @DisplayName("id로 고객을 조회한다")
    void testFindById() {
        for (Customer customer : dummyCustomers) {
            // given
            Customer insertedCustomer = customerJdbcRepository.insert(customer);

            // when
            Optional<Customer> findCustomer = customerJdbcRepository
                .findById(insertedCustomer.getCustomerId());

            // then
            assertThat(findCustomer.get()).isEqualTo(customer);
        }
    }

    @Test
    @DisplayName("name으로 고객을 조회한다")
    void testFindByName() {
        for (Customer customer : dummyCustomers) {
            // given
            Customer insertedCustomer = customerJdbcRepository.insert(customer);

            // when
            Optional<Customer> findCustomer = customerJdbcRepository
                .findByName(insertedCustomer.getName());

            // then
            assertThat(findCustomer.get()).isEqualTo(customer);
        }
    }

    @Test
    @DisplayName("email로 고객을 조회한다")
    void testFindByEmail() {
        for (Customer customer : dummyCustomers) {
            // given
            Customer insertedCustomer = customerJdbcRepository.insert(customer);

            // when
            Optional<Customer> findCustomer = customerJdbcRepository
                .findByEmail(insertedCustomer.getEmail());

            // then
            assertThat(findCustomer.get()).isEqualTo(customer);
        }
    }

    @Test
    @DisplayName("모든 데이터 삭제가 잘 되는지")
    void testDeleteAll() {
        for (Customer customer : dummyCustomers) {
            customerJdbcRepository.insert(customer);
        }

        List<Customer> customersAfterInsertion = customerJdbcRepository.findAll();
        assertThat(customersAfterInsertion.size()).isEqualTo(3);

        customerJdbcRepository.deleteAll();
        List<Customer> customersAfterDeleteAll = customerJdbcRepository.findAll();
        assertThat(customersAfterDeleteAll.size()).isEqualTo(0);
    }
}