package com.programmers.springbasic.domain.customer.repository;

import com.programmers.springbasic.domain.customer.entity.Customer;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.springbasic.domain.customer.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/testdb")
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

    Customer newCustomer, testCustomer1, testCustomer2, testCustomer3;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("testdb", classPathScript("schema-test.sql"))
                .start();

        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");

        // 미리 db에 저장 되어 있는 test data
        testCustomer1 = new Customer(UUID.fromString("c625fb58-2401-4071-b614-96136c4d9a91"), "choi", "choi@gmail.com");
        testCustomer2 = new Customer(UUID.fromString("daff49f1-61e9-40a8-9783-d4d1a77262b7"), "june", "june@gmail.com");
        testCustomer3 = new Customer(UUID.fromString("e311af99-adde-42fb-8d1c-2bcdcad83910"), "hyuk", "hyuk@gmail.com");
    }

    @AfterEach
    void reload() {
        embeddedMysql.reloadSchema("testdb", classPathScript("schema-test.sql"));
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    void save() {
        customerJdbcRepository.save(newCustomer);
        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    void findById() {
        Optional<Customer> retrievedCustomer1 = customerJdbcRepository.findById(testCustomer1.getCustomerId());

        assertThat(retrievedCustomer1.get(), samePropertyValuesAs(testCustomer1));
    }

    @Test
    void findAll() {
        List<Customer> customers = customerJdbcRepository.findAll();

        assertThat(customers.size(), is(3));
        assertThat(customers, containsInAnyOrder(
                samePropertyValuesAs(testCustomer1),
                samePropertyValuesAs(testCustomer2),
                samePropertyValuesAs(testCustomer3)
        ));
    }

    @Test
    void delete() {
        customerJdbcRepository.delete(testCustomer1.getCustomerId());

        Optional<Customer> customer = customerJdbcRepository.findById(testCustomer1.getCustomerId());
        assertThat(customer.isEmpty(), is(true));
    }
}