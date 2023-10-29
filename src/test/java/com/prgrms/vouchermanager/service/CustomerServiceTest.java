package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.exception.NotCorrectId;
import com.prgrms.vouchermanager.repository.customer.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
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
import java.util.UUID;

@SpringJUnitConfig
class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private BlacklistFileRepository blacklistFileRepository;
    private CustomerService service;
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
        blacklistFileRepository = new BlacklistFileRepository("src/main/resources/customer_blacklist.csv");
        customerRepository = new CustomerRepository(dataSource, new BlacklistFileRepository("src/main/resources/customer_blacklist.csv"));
        service = new CustomerService(blacklistFileRepository, customerRepository);

        customerRepository.create(customer2);
    }

    @AfterEach
    void afterEach() {
        template.execute(DELETE_CUSTOMERS_QUERY);
    }

    @Test
    @DisplayName("blacklist")
    void blacklist() {
        List<Customer> blacklist = service.findBlacklist();

        Assertions.assertThat(blacklist.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("create")
    void create() {
        Customer customer = service.create("스카라무슈", 1999);

        Assertions.assertThat(customer.getName()).isEqualTo("스카라무슈");
        Assertions.assertThat(customer.getYearOfBirth()).isEqualTo(1999);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Customer> list = service.findAll();

        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("updateYearOfBirth - success")
    void updateYearOfBirth() {
        Customer customer = service.updateYearOfBirth(customer2.getId(), 2000);
        Assertions.assertThat(customer.getYearOfBirth()).isEqualTo(2000);
    }

    @Test
    @DisplayName("updateYearOfBirth - 존재하지 않는 아이디")
    void updateYearOfBirthFail() {
        org.junit.jupiter.api.Assertions.assertThrows(NotCorrectId.class, () -> {
            Customer customer = service.updateYearOfBirth(UUID.randomUUID(), 2000);
        });
    }

    @Test
    @DisplayName("updateName - success")
    void updateName() {
        Customer customer = service.updateName(customer2.getId(), "벤티");
        Assertions.assertThat(customer.getName()).isEqualTo("벤티");
    }

    @Test
    @DisplayName("updateName - 존재하지 않는 아이디")
    void updateNameFail() {
        org.junit.jupiter.api.Assertions.assertThrows(NotCorrectId.class, () -> {
            Customer customer = service.updateName(UUID.randomUUID(), "벤티");
        });
    }

    @Test
    @DisplayName("delete - success")
    void delete() {
        Assertions.assertThat(service.delete(customer2.getId())).isEqualTo(1);
    }

    @Test
    @DisplayName("delete - 존재하지 않는 아이디")
    void deleteFail() {
        org.junit.jupiter.api.Assertions.assertThrows(NotCorrectId.class, () -> {
            int delete = service.delete(UUID.randomUUID());
        });
    }
}
