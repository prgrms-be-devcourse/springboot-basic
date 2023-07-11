package com.programmers.voucher.stream.customer;

import com.programmers.voucher.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class JdbcCustomerStreamTest {

    @Autowired
    CustomerStream customerStream;

    @Test
    @DisplayName("Customer 저장 로직 검증")
    void save() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-1", "user@naver.com");

        // when
        customerStream.save(customer);

        // then
        assertThat(customerStream.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Customer 단건 조회 검증")
    void findById() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-1", "user@naver.com");
        customerStream.save(customer);
        // when
        Customer findCustomer = customerStream.findById(customer.getCustomerId()).get();
        // then
        assertAll(
                () -> assertThat(findCustomer.getCustomerId()).isEqualTo(findCustomer.getCustomerId()),
                () -> assertThat(findCustomer.getName()).isEqualTo(customer.getName()),
                () -> assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail())
        );
    }

    @Test
    @DisplayName("Customer 다건 조회 검증")
    void findAll() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-1", "user@naver.com");
        Customer customer2 = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-2", "user2@naver.com");
        customerStream.save(customer);
        customerStream.save(customer2);

        // when
        List<Customer> customerList = customerStream.findAll();

        // then
        assertThat(customerList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Customer 업데이트 검증")
    void update() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-1", "user@naver.com");
        customerStream.save(customer);
        // when
        customerStream.update(customer.getCustomerId(), "user-2");
        // then
        Customer findCustomer = customerStream.findById(customer.getCustomerId()).get();
        assertThat(findCustomer.getName()).isEqualTo("user-2");
    }

    @Test
    @DisplayName("Customer 삭제 검증")
    void deleteById() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-1", "user@naver.com");
        customerStream.save(customer);

        // when
        customerStream.deleteById(customer.getCustomerId());

        // then
        assertThat(customerStream.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Customer 다중 삭제 검증")
    void deleteAll() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-1", "user@naver.com");
        Customer customer2 = new Customer(UUID.randomUUID().toString().substring(0, 7), "user-2", "user2@naver.com");
        customerStream.save(customer);
        customerStream.save(customer2);
        // when
        customerStream.deleteAll();
        // then
        assertThat(customerStream.findAll().size()).isEqualTo(0);
    }
}
