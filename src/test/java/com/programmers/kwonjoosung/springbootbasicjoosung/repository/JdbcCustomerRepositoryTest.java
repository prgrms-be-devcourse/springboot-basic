package com.programmers.kwonjoosung.springbootbasicjoosung.repository;


import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.JdbcCustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "com.programmers.kwonjoosung.springbootbasicjoosung.repository")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("customers-schema.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    void setUp() {
        this.jdbcCustomerRepository = new JdbcCustomerRepository(jdbcTemplate);
    }


    @Test
    @DisplayName("고객 저장 테스트")
    void insertTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(),"joosung");
        //when
        jdbcCustomerRepository.insert(customer.getCustomerId(), customer.getName());
        //then
        assertEquals(customer,jdbcCustomerRepository.findById(customer.getCustomerId()));
    }

    @Test
    @DisplayName("고객 조회 테스트")
    void findByIdTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(),"joosung");
        //when
        jdbcCustomerRepository.insert(customer.getCustomerId(), customer.getName());
        //then
        assertEquals(customer,jdbcCustomerRepository.findById(customer.getCustomerId()));
    }

    @Test
    @DisplayName("고객 전체 조회 테스트")
    @Disabled
    void findAllTest() {
        //given
        Customer customer1 = new Customer(UUID.randomUUID(),"joosung");
        Customer customer2 = new Customer(UUID.randomUUID(),"joosung2");
        //when
        jdbcCustomerRepository.insert(customer1.getCustomerId(), customer1.getName());
        jdbcCustomerRepository.insert(customer2.getCustomerId(), customer2.getName());
        //then
        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertEquals(customer1,customers.get(0));
        assertEquals(customer2,customers.get(1));
    }



}