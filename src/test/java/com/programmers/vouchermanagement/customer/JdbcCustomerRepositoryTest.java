package com.programmers.vouchermanagement.customer;

import com.programmers.vouchermanagement.VoucherManagementApplication;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.repository.JdbcCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { VoucherManagementApplication.class },
        initializers = ConfigDataApplicationContextInitializer.class)
public class JdbcCustomerRepositoryTest {

    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clear() {
        jdbcTemplate.update("DELETE FROM customer");
    }

    @Test
    @DisplayName("블랙리스트 고객 명단을 조회할 수 있다.")
    void successFindAllBlack() {

        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        Customer customer2 = new Customer(UUID.randomUUID(), "이롸롸", CustomerType.NORMAL);
        Customer customer3 = new Customer(UUID.randomUUID(), "최뭐뭐", CustomerType.BLACK);
        jdbcTemplate.update("INSERT INTO customer(customer_id, name, customer_type) VALUES(UUID_TO_BIN(?), (?), (?))",
                customer1.getCustomerId().toString(),
                customer1.getName(),
                customer1.getCustomerType().toString());
        jdbcTemplate.update("INSERT INTO customer(customer_id, name, customer_type) VALUES(UUID_TO_BIN(?), (?), (?))",
                customer2.getCustomerId().toString(),
                customer2.getName(),
                customer2.getCustomerType().toString());
        jdbcTemplate.update("INSERT INTO customer(customer_id, name, customer_type) VALUES(UUID_TO_BIN(?), (?), (?))",
                customer3.getCustomerId().toString(),
                customer3.getName(),
                customer3.getCustomerType().toString());

        // when
        List<Customer> customers = customerRepository.findAllBlack();

        // then
        assertThat(customers).hasSize(2)
                .extracting(Customer::getCustomerId)
                .contains(customer1.getCustomerId(), customer3.getCustomerId());
    }

    @Test
    @DisplayName("아이디로 Customer를 조회할 수 있다.")
    void successFindById() {

        // given
        Customer customer = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        jdbcTemplate.update("INSERT INTO customer(customer_id, name, customer_type) VALUES(UUID_TO_BIN(?), (?), (?))",
                customer.getCustomerId().toString(),
                customer.getName(),
                customer.getCustomerType().toString());

        // when
        Customer savedCustomer = customerRepository.findById(customer.getCustomerId()).get();

        // then
        assertThat(savedCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(savedCustomer.getCustomerType()).isEqualTo(customer.getCustomerType());
    }

    @Test
    @DisplayName("올바르지 않은 아이디는 Customer 조회 시 빈 Optional 객체를 생성한다.")
    void failFindById() {

        // given
        Customer customer = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        jdbcTemplate.update("INSERT INTO customer(customer_id, name, customer_type) VALUES(UUID_TO_BIN(?), (?), (?))",
                customer.getCustomerId().toString(),
                customer.getName(),
                customer.getCustomerType().toString());

        // when
        Optional<Customer> savedCustomer = customerRepository.findById(UUID.randomUUID());

        // then
        assertThat(savedCustomer.isPresent()).isEqualTo(false);
    }
}
