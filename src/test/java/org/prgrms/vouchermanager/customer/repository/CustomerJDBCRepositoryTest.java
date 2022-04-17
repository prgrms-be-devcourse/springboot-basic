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
    Customer testCustomer;

    @BeforeAll
    void beforeAll() {
        customerJdbcRepository.deleteAll();
        testCustomer = new Customer(UUID.randomUUID(), "test_customer", "test@email.com");
    }

    @Test
    void insert_고객을_삽입할_수_있다() {
        //when
        customerJdbcRepository.insert(testCustomer);
        Customer findCustomer = customerJdbcRepository.findById(testCustomer.getCustomerId()).get();

        //then
        assertThat(testCustomer.equals(findCustomer)).isTrue();
    }

    @Test
    void update_고객을_업데이트_할_수_있다() {
        //when
        customerJdbcRepository.insert(testCustomer);
        String updatedName = "NewName";
        testCustomer.changeName(updatedName);
        customerJdbcRepository.update(testCustomer);

        //then
        assertThat(customerJdbcRepository.findById(testCustomer.getCustomerId()).get().getName()).isEqualTo(updatedName);
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
    void findAll_모든_고객을_리스트로_조회할_수_있다() {

    }

    @Test
    void findById_customerId로_고객을_조회할_수_있다() {
    }

    @Test
    void findByName_customer_name으로_고객을_조회할_수_있다() {
    }

    @Test
    void findByEmail_customer_email로_고객을_조회할_수_있다() {
    }

    @Test
    void delete_customer_id로_고객을_삭제할_수_있다() {
    }

    @Test
    void deleteAll_고객을_전부_삭제_할_수_있다() {
    }

}