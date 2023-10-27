package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void save() {
        // given
        Customer newCustomer = Customer.fixture();

        // when
        customerRepository.save(newCustomer);

        // then
        Customer savedCustomer = customerRepository.findAll(new GetCustomersRequestDto()).get(0);
        assertThat(savedCustomer.getEmail()).isEqualTo(newCustomer.getEmail());
        assertThat(savedCustomer.isBlacklisted()).isEqualTo(newCustomer.isBlacklisted());
    }

    @Test
    @DisplayName("고객 목록을 저장할 수 있다.")
    void saveAll() {
        // given
        Customer newCustomer1 = new Customer("test1@email.com", false);
        Customer newCustomer2 = new Customer("test2@email.com", true);

        // when
        customerRepository.saveAll(List.of(newCustomer1, newCustomer2));

        // then
        List<Customer> savedCustomers = customerRepository.findAll(new GetCustomersRequestDto());

        assertThat(savedCustomers).hasSize(2);
        assertThat(savedCustomers).extracting(Customer::getEmail)
                .containsExactlyInAnyOrder(newCustomer1.getEmail(), newCustomer2.getEmail());
        assertThat(savedCustomers).extracting(Customer::isBlacklisted)
                .containsExactlyInAnyOrder(newCustomer1.isBlacklisted(), newCustomer2.isBlacklisted());
    }

    @Test
    @DisplayName("고객을 아이디로 조회할 수 있다.")
    void findById() {
        // given
        Customer newCustomer = Customer.fixture();
        customerRepository.save(newCustomer);

        Customer savedCustomer = customerRepository.findAll(new GetCustomersRequestDto()).get(0);

        // when
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getEmail()).isEqualTo(newCustomer.getEmail());
        assertThat(foundCustomer.get().isBlacklisted()).isEqualTo(newCustomer.isBlacklisted());
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    void findAll() {
        // given
        Customer newCustomer1 = new Customer("test1@email.com", false);
        Customer newCustomer2 = new Customer("test2@email.com", true);
        customerRepository.saveAll(List.of(newCustomer1, newCustomer2));

        // when
        List<Customer> foundCustomers = customerRepository.findAll(new GetCustomersRequestDto());

        // then
        assertThat(foundCustomers).hasSize(2);
        assertThat(foundCustomers).extracting(Customer::getEmail)
                .containsExactlyInAnyOrder(newCustomer1.getEmail(), newCustomer2.getEmail());
        assertThat(foundCustomers).extracting(Customer::isBlacklisted)
                .containsExactlyInAnyOrder(newCustomer1.isBlacklisted(), newCustomer2.isBlacklisted());
    }

    @Test
    @DisplayName("모든 고객을 삭제할 수 있다.")
    void deleteAll() {
        // given
        customerRepository.saveAll(List.of(
                new Customer("test1@email.com", false),
                new Customer("test2@email.com", true)
        ));

        // when
        customerRepository.deleteAll();

        // then
        List<Customer> foundCustomers = customerRepository.findAll(new GetCustomersRequestDto());
        assertThat(foundCustomers).isEmpty();
    }
}
