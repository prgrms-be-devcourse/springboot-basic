package org.prgrms.vouchermanager.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanager.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJDBCRepositoryTest {

    @Autowired
    CustomerRepository customerJdbcRepository;
    @Autowired
    DataSource dataSource;
    private Customer testCustomer1;
    private Customer testCustomer2;

    @BeforeAll
    void beforeAll() {
        customerJdbcRepository.deleteAll();
        testCustomer1 = new Customer(UUID.randomUUID(), "test_customer1", "test01@email.com");
        testCustomer2 = new Customer(UUID.randomUUID(), "test_customer2", "test02@email.com");
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.vouchermanager"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }
    }

    @Test
    @Order(1)
    void insert_고객을_삽입할_수_있다() {
        //when
        customerJdbcRepository.insert(testCustomer1);
        customerJdbcRepository.insert(testCustomer2);
        Customer findCustomer1 = customerJdbcRepository.findById(testCustomer1.getCustomerId()).get();
        Customer findCustomer2 = customerJdbcRepository.findById(testCustomer2.getCustomerId()).get();

        //then
        assertThat(testCustomer1.equals(findCustomer1)).isTrue();
        assertThat(testCustomer2.equals(findCustomer2)).isTrue();
    }

    @Test
    @Order(2)
    void findById_customerId로_고객을_조회할_수_있다() {
        //given
        UUID customerId = testCustomer1.getCustomerId();

        //when
        Customer findCustomer = customerJdbcRepository.findById(customerId).get();

        //then
        assertThat(testCustomer1.equals(findCustomer)).isTrue();
    }

    @Test
    @Order(3)
    void update_고객을_업데이트_할_수_있다() {
        //when
        String updatedName = "NewName";
        testCustomer1.changeName(updatedName);
        customerJdbcRepository.update(testCustomer1);

        //then
        assertThat(customerJdbcRepository.findById(testCustomer1.getCustomerId()).get().getName()).isEqualTo(updatedName);
    }

    @Test
    @Order(4)
    void findByName_customer_name으로_고객을_조회할_수_있다() {
        //given
        String name = testCustomer1.getName();

        //when
        Customer findCustomer = customerJdbcRepository.findByName(name).get();

        //then
        assertThat(testCustomer1.equals(findCustomer)).isTrue();
    }

    @Test
    @Order(5)
    void findByEmail_customer_email로_고객을_조회할_수_있다() {
        //given
        String email = testCustomer1.getEmail();

        //when
        Customer findCustomer = customerJdbcRepository.findByEmail(email).get();

        //then
        assertThat(testCustomer1.equals(findCustomer)).isTrue();
    }

    @Test
    @Order(6)
    void findAll_모든_고객을_리스트로_조회할_수_있다() {
        //given
        List<Customer> expectList = List.of(testCustomer1, testCustomer2);

        //when
        List<Customer> all = customerJdbcRepository.findAll();

        //then
        assertThat(all).containsAll(expectList);
    }

    @Test
    @Order(7)
    void delete_customer_id로_고객을_삭제할_수_있다() {
        //when
        customerJdbcRepository.delete(testCustomer1.getCustomerId());

        //then
        assertThat(customerJdbcRepository.findById(testCustomer1.getCustomerId())).isEqualTo(Optional.empty());
    }

    @Test
    @Order(8)
    void deleteAll_고객을_전부_삭제_할_수_있다() {
        //when
        customerJdbcRepository.insert(testCustomer1);
        customerJdbcRepository.deleteAll();

        //then
        assertThat(customerJdbcRepository.findAll()).containsAll(List.of());
    }

}