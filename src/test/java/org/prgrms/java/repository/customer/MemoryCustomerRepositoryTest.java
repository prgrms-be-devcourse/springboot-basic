package org.prgrms.java.repository.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

class MemoryCustomerRepositoryTest {
    private static final CustomerRepository customerRepository = new MemoryCustomerRepository();

    @BeforeEach
    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("정상/블랙 유저를 등록할 수 있다.")
    void testInsert() {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        Customer blockedCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("other-test")
                .email("other-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();

        Customer insertedCustomer = customerRepository.save(customer);
        Customer insertedBlockedCustomer = customerRepository.save(blockedCustomer);

        assertThat(customer, samePropertyValuesAs(insertedCustomer));
        assertThat(blockedCustomer, samePropertyValuesAs(insertedBlockedCustomer));
    }

    @Test
    @DisplayName("동일한 ID의 유저는 등록할 수 없다.")
    void testInsertSameIdCustomer() {
        assertThrows(CustomerException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = Customer.builder()
                    .customerId(customerId)
                    .name("test")
                    .email("test@gmail.com")
                    .createdAt(LocalDateTime.now())
                    .isBlocked(false)
                    .build();
            Customer otherCustomer = Customer.builder()
                    .customerId(customerId)
                    .name("other-test")
                    .email("other-test@gmail.com")
                    .createdAt(LocalDateTime.now())
                    .isBlocked(false)
                    .build();

            customerRepository.save(customer);
            customerRepository.save(otherCustomer);
        });
    }

    @Test
    @DisplayName("등록한 유저를 ID, 이름, 이메일로 찾을 수 있다.")
    void testFindById() {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        Customer otherCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("other-test")
                .email("other-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        Customer anotherCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("another-test")
                .email("another-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);
        customerRepository.save(anotherCustomer);

        assertThat(customerRepository.findById(customer.getCustomerId()).get(), samePropertyValuesAs(customer));
        assertThat(customerRepository.findByName(otherCustomer.getName()).get(), samePropertyValuesAs(otherCustomer));
        assertThat(customerRepository.findByEmail(anotherCustomer.getEmail()).get(), samePropertyValuesAs((anotherCustomer)));
    }

    @Test
    @DisplayName("등록한 유저와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        Customer otherCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("other-test")
                .email("other-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);

        assertThat(customerRepository.findAll().isEmpty(), is(false));
        assertThat(customerRepository.findAll(), hasSize(2));
    }


    @Test
    @DisplayName("유저를 등록한 후에 필드를 변경할 수 있다.")
    void testUpdate() {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        customerRepository.save(customer);

        Customer update = Customer.builder()
                .customerId(customer.getCustomerId())
                .name("updated-test")
                .email("updated-test@gmail.com")
                .isBlocked(customer.isBlocked())
                .build();
        update = customerRepository.update(update);

        assertThat(customer, not(samePropertyValuesAs(update)));
    }

    @Test
    @DisplayName("등록한 유저와 전체 삭제한 개수가 같다.")
    void testDeleteAll() {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        Customer otherCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("other-test")
                .email("other-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
        Customer blockedCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("unknown")
                .email("spam@spam.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(true)
                .build();

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);
        customerRepository.save(blockedCustomer);

        assertThat(customerRepository.deleteAll(), is(3L));
    }
}