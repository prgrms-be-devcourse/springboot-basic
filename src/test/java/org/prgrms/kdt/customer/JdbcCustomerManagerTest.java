package org.prgrms.kdt.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.JdbcBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcCustomerManagerTest extends JdbcBase {

    @Autowired
    private CustomerManager customerManager;

    @BeforeEach
    void init() {
        customerManager.deleteAll();
    }

    @DisplayName("고객은 저장될 수 있다.")
    @Test
    void saveTest() {
        // given
        long customerId = 1L;
        Customer customer = new Customer(customerId, "test1");

        // when
        customerManager.save(customer);

        // then
        assertThat(customerManager.findById(customerId).isPresent())
                .isTrue();
    }

    @DisplayName("모든 고객을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        long customer1Id = 1L;
        Customer customer1 = new Customer(customer1Id, "test12");
        long customer2Id = 1L;
        Customer customer2 = new Customer(customer2Id, "test2");
        customerManager.save(customer1);
        customerManager.save(customer2);

        // when
        List<Customer> actualCustomers = customerManager.findAll();

        // then
        assertThat(actualCustomers)
                .usingRecursiveComparison()
                .isEqualTo(List.of(customer1, customer2));
    }

    @DisplayName("아이디로 고객의 정보를 조회할 수 있다.")
    @Test
    void findIdTest() {
        // given
        long customerId = 1L;
        Customer customer = new Customer(customerId, "test1");
        customerManager.save(customer);

        // when


        // then
        assertThat(customerManager.findById(customerId).isPresent())
                .isTrue();
    }

    @DisplayName("고객의 정보를 수정할 수 있다.")
    @Test
    void updateTest() {
        // given
        long customerId = 1L;
        Customer customer = new Customer(customerId, "test1");
        customerManager.save(customer);
        Customer updatedCustomer = new Customer(customerId, "update");

        // when
        customerManager.update(updatedCustomer);

        // then
        customerManager.findById(customerId).ifPresent(
                actual -> assertThat(actual)
                        .usingRecursiveComparison()
                        .isEqualTo(updatedCustomer)
        );

    }

    @DisplayName("고객을 삭제할 수 있다.")
    @Test
    void deleteByIdTest() {
        // given
        long customerId = 1L;
        Customer customer = new Customer(customerId, "test1");
        customerManager.save(customer);
        Customer updatedCustomer = new Customer(customerId, "update");

        // when
        customerManager.deleteById(customerId);

        // then
        assertThat(customerManager.findById(customerId).isPresent())
                .isFalse();
    }
}