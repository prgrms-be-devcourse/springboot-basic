package com.zerozae.voucher.repository.customer;


import com.zaxxer.hikari.HikariDataSource;
import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
public class JdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"com.zerozae.voucher.repository"}
    )
    static class testConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mysql.getJdbcUrl())
                    .username("root")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcCustomerRepository(jdbcTemplate);
        }
    }

    static JdbcDatabaseContainer mysql = new MySQLContainer("mysql:8.0.26")
            .withDatabaseName("test_mgmt")
            .withInitScript("schema.sql");

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    Customer normalCustomer;
    Customer blacklistCustomer;

    @BeforeAll
    static void setUp(){
        mysql.start();
    }

    @AfterAll
    static void cleanUp(){
        mysql.stop();
    }

    @BeforeEach
    void setCustomer(){
        normalCustomer = new Customer(UUID.randomUUID(), "normalUser", CustomerType.NORMAL);
        blacklistCustomer= new Customer(UUID.randomUUID(), "blackUser", CustomerType.BLACKLIST);
    }

    @AfterEach
    void cleanRepository(){
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void saveCustomer_Success_Test(){
        // Given

        // When
        Customer savedCustomer = jdbcCustomerRepository.save(normalCustomer);

        // Then
        assertThat(savedCustomer, equalTo(normalCustomer));
        assertThat(savedCustomer.getCustomerId(), is(normalCustomer.getCustomerId()));
        assertThat(savedCustomer.getCustomerName(), is(normalCustomer.getCustomerName()));
    }

    @Test
    @DisplayName("회원 전체 조회 테스트")
    void findAllCustomers_Success_Test(){
        // Given
        jdbcCustomerRepository.save(normalCustomer);
        jdbcCustomerRepository.save(blacklistCustomer);

        // When
        List<Customer> customers = jdbcCustomerRepository.findAll();

        // Then
        assertThat(customers, containsInAnyOrder(
                hasProperty("customerId", is(normalCustomer.getCustomerId())),
                hasProperty("customerId", is(blacklistCustomer.getCustomerId()))
        ));
    }

    @Test
    @DisplayName("회원 아이디 조회 테스트")
    void findCustomerById_Success_Test(){
        // Given
        jdbcCustomerRepository.save(normalCustomer);
        jdbcCustomerRepository.save(blacklistCustomer);
        UUID customerId = normalCustomer.getCustomerId();

        // When
        Optional<Customer> findCustomer = jdbcCustomerRepository.findById(customerId);

        // Then
        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get().getCustomerName(), is(normalCustomer.getCustomerName()));
        assertThat(findCustomer.get().getCustomerType(), is(normalCustomer.getCustomerType()));
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디 조회 테스트")
    void findCustomerById_NotExist_Failed(){
        // Given
        UUID notExistId = UUID.randomUUID();

        // When
        Optional<Customer> customer = jdbcCustomerRepository.findById(notExistId);

        // Then
        assertTrue(customer.isEmpty());
    }

    @Test
    @DisplayName("회원 아이디로 회원 삭제 테스트")
    void deleteCustomerById_SuccessTest() {
        // Given
        jdbcCustomerRepository.save(normalCustomer);
        jdbcCustomerRepository.save(blacklistCustomer);

        // When
        jdbcCustomerRepository.deleteById(normalCustomer.getCustomerId());

        // Then
        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertThat(customers, not(contains(samePropertyValuesAs(normalCustomer))));
        assertThat(customers, contains(samePropertyValuesAs(blacklistCustomer)));
    }

    @Test
    @DisplayName("전체 회원 삭제 테스트")
    void deleteAllCustomers_Success_Test(){
        // Given
        jdbcCustomerRepository.save(normalCustomer);
        jdbcCustomerRepository.save(blacklistCustomer);

        // When
        jdbcCustomerRepository.deleteAll();

        // Then
        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertThat(customers, not(allOf(
                hasItem(hasProperty("customerId", is(normalCustomer.getCustomerId()))),
                hasItem(hasProperty("customerId", is(blacklistCustomer.getCustomerId())))
        )));
        assertTrue(customers.isEmpty());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void updateCustomer_Success_Test(){
        // Given
        jdbcCustomerRepository.save(normalCustomer);
        CustomerRequest customerRequest = new CustomerRequest("진상 고객", CustomerType.BLACKLIST);

        // When
        jdbcCustomerRepository.update(normalCustomer.getCustomerId(), customerRequest);

        // Then
        Optional<Customer> updatedCustomer = jdbcCustomerRepository.findById(normalCustomer.getCustomerId());
        assertEquals(updatedCustomer.get().getCustomerName(), customerRequest.getCustomerName());
        assertEquals(updatedCustomer.get().getCustomerType(), customerRequest.getCustomerType());
    }
}
