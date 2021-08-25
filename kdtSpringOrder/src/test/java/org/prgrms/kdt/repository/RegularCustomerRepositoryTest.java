package org.prgrms.kdt.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.RegularCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegularCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
        }
    }

    @Autowired
    RegularCustomerRepository regularCustomerRepository;

    @Autowired
    DataSource dataSource;

    RegularCustomer newCustomer;

    @BeforeAll
    void setUp() {
        newCustomer = new RegularCustomer(UUID.randomUUID(), "test1-user", "test1-user@gmail.com", LocalDateTime.now());
        regularCustomerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        regularCustomerRepository.insert(newCustomer);
        Optional<Customer> retrievedCustomer = regularCustomerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        List<Customer> customers = regularCustomerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        Optional<Customer> customer = regularCustomerRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknownCustomer = regularCustomerRepository.findByName("unknown-user");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("메일로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        Optional<Customer> customer = regularCustomerRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknownCustomer = regularCustomerRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        newCustomer.changeName("updated-user");
        regularCustomerRepository.update(newCustomer);

        List<Customer> allCustomers = regularCustomerRepository.findAll();
        assertThat(allCustomers, hasSize(1));
        assertThat(allCustomers, everyItem(samePropertyValuesAs(newCustomer)));

        Optional<Customer> retrievedCustomer = regularCustomerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

}