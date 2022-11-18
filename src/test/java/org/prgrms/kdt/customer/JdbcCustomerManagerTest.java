package org.prgrms.kdt.customer;

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
        String customerName = "test1";
        Customer customer = new Customer(customerName);

        // when
        Customer savedCustomer = customerManager.save(customer);

        // then
        assertThat(customerManager.findById(savedCustomer.getId()).isPresent())
                .isTrue();
    }

    @DisplayName("모든 고객을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        Customer customer1 = new Customer("test1");
        Customer customer2 = new Customer("test2");
        Customer savedCustomer1 = customerManager.save(customer1);
        Customer savedCustomer2 = customerManager.save(customer2);

        // when
        List<Customer> actualCustomers = customerManager.findAll();

        // then

        assertThat(actualCustomers)
                .usingRecursiveComparison()
                .isEqualTo(List.of(savedCustomer1, savedCustomer2));
    }

    @DisplayName("아이디로 고객의 정보를 조회할 수 있다.")
    @Test
    void findIdTest() {
        // given
        Customer customer = new Customer("test");
        Customer savedCustomer = customerManager.save(customer);

        // when
        Optional<Customer> actual = customerManager.findById(savedCustomer.getId());

        // then
        assertThat(actual.isPresent())
                .isTrue();
    }

    @DisplayName("고객의 정보를 수정할 수 있다.")
    @Test
    void updateTest() {
        // given
        Customer customer = new Customer("test1");
        Customer savedCustomer = customerManager.save(customer);
        Customer updatedCustomer = new Customer(savedCustomer.getId(), "update");

        // when
        customerManager.update(updatedCustomer);
        Optional<Customer> actual = customerManager.findById(updatedCustomer.getId());

        // then
        assertThat(actual)
                .isPresent()
                .map(Customer::getName)
                .hasValue("update");

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