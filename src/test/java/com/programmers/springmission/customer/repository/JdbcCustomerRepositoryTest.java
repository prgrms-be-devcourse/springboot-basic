package com.programmers.springmission.customer.repository;

import com.programmers.springmission.ManagementController;
import com.programmers.springmission.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcCustomerRepository.class)
class JdbcCustomerRepositoryTest {

    @Autowired
    private JdbcCustomerRepository repository;

    @MockBean
    private ManagementController managementController;

    @DisplayName("Customer 가 repository 에 저장 성공하는지 테스트")
    @Test
    void repository_save_success() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "신재윤", "aaa@email.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "데브유니", "devUni@email.com", LocalDateTime.now());

        // when
        repository.save(customer1);
        repository.save(customer2);

        // then
        List<Customer> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(customer1, customer2);
    }

    @DisplayName("Customer 수정 성공하는지 테스트")
    @Test
    void repository_update_success() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "신재윤", "aaa@email.com", LocalDateTime.now());
        repository.save(customer);

        // when
        customer.updateName("재윤 신");
        repository.update(customer);

        // then
        List<Customer> all = repository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(customer.getName());
    }

    @DisplayName("Customer 단건 삭제 성공하는지 테스트")
    @Test
    void repository_delete_byId_success() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "신재윤", "aaa@email.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "데브유니", "devUni@email.com", LocalDateTime.now());

        // when
        repository.save(customer1);
        repository.save(customer2);
        repository.deleteById(customer2.getCustomerId());

        // then
        List<Customer> all = repository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getCustomerId()).isEqualTo(customer1.getCustomerId());
    }

    @DisplayName("Customer 전부 삭제 성공하는지 테스트")
    @Test
    void repository_delete_all_success() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "신재윤", "aaa@email.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "데브유니", "devUni@email.com", LocalDateTime.now());

        // when
        repository.save(customer1);
        repository.save(customer2);
        repository.deleteAll();

        // then
        List<Customer> all = repository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}