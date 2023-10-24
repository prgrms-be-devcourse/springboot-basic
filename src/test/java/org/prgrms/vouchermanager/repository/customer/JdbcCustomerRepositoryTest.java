package org.prgrms.vouchermanager.repository.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JdbcCustomerRepositoryTest {

    private final JdbcCustomerRepository repository;

    @Autowired
    JdbcCustomerRepositoryTest(JdbcCustomerRepository repository) {
        this.repository = repository;
    }

    @Test
    @DisplayName("customer 저장에 성공해야한다.")
    void save() {
        Customer customer = new Customer(UUID.randomUUID(), "jun", "123@", true);
        repository.save(customer);

        Optional<Customer> afterSave = repository.findById(customer.getCustomerId());

        assertThat(afterSave).isNotEmpty();
    }

    @Test
    @DisplayName("전체 고객 조회에 성공한다")
    void findAll() {
        Customer customer = new Customer(UUID.randomUUID(), "jun", "123@", true);
        repository.save(customer);

        List<Customer> all = repository.findAll();

        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("id를 통해 고객을 조회할 수 있다")
    void findById(){
        Customer customer = new Customer(UUID.randomUUID(), "jun", "123@", true);
        repository.save(customer);

        Optional<Customer> findCustomer = repository.findById(customer.getCustomerId());

        assertAll(
                () -> assertThat(customer.getCustomerId()).isEqualTo(findCustomer.get().getCustomerId()),
                () -> assertThat(customer.getName()).isEqualTo(findCustomer.get().getName()),
                () -> assertThat(customer.getEmail()).isEqualTo(findCustomer.get().getEmail()),
                () -> assertThat(customer.getIsBlack()).isEqualTo(findCustomer.get().getIsBlack())
        );
    }



}