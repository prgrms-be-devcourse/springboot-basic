package org.prgrms.vouchermanager.domain.customer.persistence;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.customer.domain.Customer;
import org.prgrms.vouchermanager.domain.customer.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerJdbcRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    void boforeEach() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 삽입할 수 있다")
    void insert_customer를_삽입할_수_있다() {
        Customer customer = Customer.create("customer01", "testEmail01@email.com");

        customerJdbcRepository.insert(customer);
        Customer foundCustomer = customerJdbcRepository.findById(customer.getId()).get();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("고객을 id로 조회할 수 있다")
    void findById_id로_조회할_수_있다() {
        Customer customer = Customer.create("customer02", "testEmail02@email.com");
        customerJdbcRepository.insert(customer);

        UUID customerId = customer.getId();
        Customer foundCustomer = customerJdbcRepository.findById(customerId).get();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("고객을 name으로 조회할 수 있다")
    void findByName_name으로_조회할_수_있다() {
        Customer customer = Customer.create("customer03", "testEmail04@email.com");
        customerJdbcRepository.insert(customer);

        Customer findCustomer = customerJdbcRepository.findByName(customer.getName()).get();

        assertThat(findCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("고객을 email로 조회할 수 있다")
    void findByEmail_email로_조회할_수_있다() {
        Customer customer = Customer.create("customer05", "testEmail05@email.com");
        customerJdbcRepository.insert(customer);

        Customer foundCustomer = customerJdbcRepository.findByEmail(customer.getEmail()).get();

        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다")
    void findAll_모든_고객을_조회할_수_있다() {
        List<Customer> expectList = List.of(
                Customer.create("customer06", "testEmail06@email.com"),
                Customer.create("customer07", "testEmail07@email.com"));
        expectList.forEach(customer -> customerJdbcRepository.insert(customer));

        List<Customer> all = customerJdbcRepository.findAll();

        assertThat(all).containsAll(expectList);
    }

    @Test
    @DisplayName("고객을 id로 삭제할 수 있다")
    void delete_id로_고객을_삭제할_수_있다() {
        Customer customer = Customer.create("customer08", "testEmail08@email.com");
        customerJdbcRepository.insert(customer);

        customerJdbcRepository.delete(customer.getId());

        assertThat(customerJdbcRepository.findById(customer.getId())).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("고객을 전부 삭제 할 수 있다")
    void deleteAll_전부_삭제_할_수_있다() {
        Customer customer = Customer.create("customer09", "testEmail09@email.com");
        customerJdbcRepository.insert(customer);

        customerJdbcRepository.deleteAll();

        assertThat(customerJdbcRepository.findAll()).containsAll(List.of());
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.vouchermanager.domain.customer"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/test_order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
