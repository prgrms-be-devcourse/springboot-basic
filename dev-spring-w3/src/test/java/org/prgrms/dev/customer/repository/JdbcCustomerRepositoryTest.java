package org.prgrms.dev.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.dev.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void createTest() {
        Customer customer = new Customer(UUID.randomUUID(), "tester03", "test03@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.create(customer);

        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.orElse(null)).isNotNull();
        assertThat(retrievedCustomer.get().getName()).isEqualTo(customer.getName());

    }

    @DisplayName("중복된 이메일로 고객을 추가할 수 없다.")
    @Test
    void createByDuplicateEmailTest() {
        Customer customer = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now());

        assertThatThrownBy(() -> jdbcCustomerRepository.create(customer))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("This email already exists");
    }

    @DisplayName("이메일로 고객 정보를 조회할 수 있다.")
    @Test
    void findByEmailTest() {
        String email = "test02@gmail.com";

        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findByEmail(email);

        assertThat(retrievedCustomer.orElse(null)).isNotNull();
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.dev.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3307/dev_springboot_order")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

}
