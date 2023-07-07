package com.programmers.vouchermanagement.customer.infrastructure;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerRepository;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcTemplateCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("고객을 저장한다.")
    void save() {
        // given
        Customer customer = new Customer("고객", CustomerType.WHITE);

        // when
        customerRepository.save(customer);

        // then
        Customer result = customerRepository.findById(customer.getId()).get();
        assertThat(result).isEqualTo(customer);
    }

    @Test
    @DisplayName("Id로 고객을 조회한다.")
    void findById() {
        // given
        Customer customer = new Customer("고객", CustomerType.WHITE);
        customerRepository.save(customer);

        // when
        Customer result = customerRepository.findById(customer.getId()).get();

        // then
        assertThat(result).isEqualTo(customer);
    }

    @Test
    @DisplayName("고객을 모두 조회한다.")
    void findAll() {
        // given
        Customer customer1 = new Customer("고객1", CustomerType.WHITE);
        Customer customer2 = new Customer("고객2", CustomerType.BLACK);

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // when
        List<Customer> result = customerRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("고객 정보를 업데이트한다.")
    void update() {
        // given
        Customer customer = new Customer("고객", CustomerType.WHITE);
        customerRepository.save(customer);
        customer.changeType(CustomerType.BLACK);

        // when
        customerRepository.update(customer);

        // then
        assertThat(customer.getType()).isEqualTo(CustomerType.BLACK);
    }

    @Test
    @DisplayName("고객을 삭제한다.")
    void deleteById() {
        // given
        Customer customer = new Customer("고객", CustomerType.WHITE);

        // when
        customerRepository.deleteById(customer.getId());

        // then
        Optional<Customer> result = customerRepository.findById(customer.getId());
        assertThat(result).isEmpty();
    }
}