package org.prgrms.kdt.service;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.prgrms.kdt.repository.customer.JdbcCustomerRepository;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.service.dto.RequestCreateCustomerDto;
import org.prgrms.kdt.service.dto.RequestUpdateCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class CustomerServiceTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt_test")
                    .username("root")
                    .password("admin")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        public CustomerRepository customerRepository() {
            return new JdbcCustomerRepository(jdbcTemplate());
        }

        @Bean
        public CustomerService customerService() {
            return new CustomerService(customerRepository());
        }
    }

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객이 저장된다")
    void insert() {
        // given
        RequestCreateCustomerDto dto = new RequestCreateCustomerDto("함승훈", "test@naver.com", LocalDateTime.now());

        // when
        Customer customer = customerService.insert(dto);

        // then
        int count = customerRepository.count();
        Optional<Customer> found = customerRepository.findById(customer.getCustomerId());
        assertThat(count).isEqualTo(1);
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("중복 이메일은 허용되지 않는다")
    public void testDuplicateEmailInsert() throws Exception {
        // given
        RequestCreateCustomerDto dto1 = new RequestCreateCustomerDto("함승훈", "test@naver.com", LocalDateTime.now());
        RequestCreateCustomerDto dto2 = new RequestCreateCustomerDto("가나다", "test@naver.com", LocalDateTime.now());

        Customer customer = customerService.insert(dto1);
        // when
        try {
            customerService.insert(dto2);
        } catch (RuntimeException e){

        }

        // then
        int count = customerRepository.count();
        Optional<Customer> found = customerRepository.findById(customer.getCustomerId());
        assertThat(count).isEqualTo(1);
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }


    @Test
    @DisplayName("저장된 모든 고객을 가져올 수 있다")
    void customers() {
        // given
        RequestCreateCustomerDto dto1 = new RequestCreateCustomerDto("함승훈", "test1@naver.com", LocalDateTime.now());
        RequestCreateCustomerDto dto2 = new RequestCreateCustomerDto("가나다", "test2@naver.com", LocalDateTime.now());

        // when
        Customer customer1 = customerService.insert(dto1);
        Customer customer2 = customerService.insert(dto2);

        // then
        List<Customer> customers = customerService.customers();
        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("고객의 이름을 변경할 수 있다")
    void update() {
        // given
        RequestCreateCustomerDto dto = new RequestCreateCustomerDto("함승훈", "test@naver.com", LocalDateTime.now());
        Customer customer = customerService.insert(dto);

        // when
        Customer updated = customerService.update(new RequestUpdateCustomerDto(customer.getCustomerId(), "updated name"));

        // then
        Optional<Customer> found = customerRepository.findById(updated.getCustomerId());
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(found.get().getName()).isNotEqualTo(customer.getName());
        assertThat(found.get().getName()).isEqualTo("updated name");
    }
}