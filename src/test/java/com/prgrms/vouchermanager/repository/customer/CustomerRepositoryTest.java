package com.prgrms.vouchermanager.repository.customer;

import com.prgrms.vouchermanager.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

@SpringJUnitConfig
class CustomerRepositoryTest {

    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate template;
    @Autowired private DataSource dataSource;
    private final Customer customer1 = new Customer("스카라무슈", 1995);
    private final Customer customer2 = new Customer("종려", 1990);
    private final static String DELETE_CUSTOMERS_QUERY = "delete from customers;";

    @Configuration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/voucher_manager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                    .username("root")
                    .password("suzzingV1999@")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @BeforeEach
    void beforeEach() {
        customerRepository = new CustomerRepository(dataSource, new BlacklistFileRepository("src/main/resources/customer_blacklist.csv"));
        customerRepository.create(customer2);
    }

    @AfterEach
    void afterEach() {
        template.execute(DELETE_CUSTOMERS_QUERY);
    }

    @Test
    @DisplayName("create")
    void create() {
        Customer customer = customerRepository.create(customer1);

        Assertions.assertThat(customer.getName()).isEqualTo("스카라무슈");
        Assertions.assertThat(customer.getYearOfBirth()).isEqualTo(1995);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Customer> list = customerRepository.list();

        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Customer customer = customerRepository.create(customer1);
        Customer findVoucher = customerRepository.findById(customer.getId());

        Assertions.assertThat(findVoucher.getName()).isEqualTo(customer.getName());
        Assertions.assertThat(findVoucher.getYearOfBirth()).isEqualTo(customer.getYearOfBirth());
    }

    @Test
    @DisplayName("updateYearOfBirth")
    void updateYearOfBirth() {
        Customer updateCustomer = customerRepository.updateYearOfBirth(customer2.getId(), 2000);
        Assertions.assertThat(updateCustomer.getYearOfBirth()).isEqualTo(2000);
    }

    @Test
    @DisplayName("updateName")
    void updateName() {
        Customer updateCustomer = customerRepository.updateName(customer2.getId(), "벤티");
        Assertions.assertThat(updateCustomer.getName()).isEqualTo("벤티");
    }

    @Test
    @DisplayName("delete")
    void delete() {
        Assertions.assertThat(customerRepository.delete(customer2.getId())).isEqualTo(1);
    }
}
