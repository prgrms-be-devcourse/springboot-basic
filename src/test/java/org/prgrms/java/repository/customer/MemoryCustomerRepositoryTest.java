package org.prgrms.java.repository.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.badrequest.CustomerBadRequestException;

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
        Customer customer = createCustomer(UUID.randomUUID());
        Customer blockedCustomer = createBlockedCustomer(UUID.randomUUID());

        Customer insertedCustomer = customerRepository.save(customer);
        Customer insertedBlockedCustomer = customerRepository.save(blockedCustomer);

        assertThat(customer, samePropertyValuesAs(insertedCustomer));
        assertThat(blockedCustomer, samePropertyValuesAs(insertedBlockedCustomer));
    }

    @Test
    @DisplayName("동일한 ID의 유저는 등록할 수 없다.")
    void testInsertSameIdCustomer() {
        assertThrows(CustomerBadRequestException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = createCustomer(customerId);
            Customer otherCustomer = createOtherCustomer(customerId);

            customerRepository.save(customer);
            customerRepository.save(otherCustomer);
        });
    }

    @Test
    @DisplayName("등록한 유저를 ID, 이름, 이메일로 찾을 수 있다.")
    void testFindById() {
        Customer customer = createCustomer(UUID.randomUUID());
        Customer otherCustomer = createOtherCustomer(UUID.randomUUID());
        Customer blockedCustomer = createBlockedCustomer(UUID.randomUUID());

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);
        customerRepository.save(blockedCustomer);

        assertThat(customerRepository.findById(customer.getCustomerId()).orElseThrow(), samePropertyValuesAs(customer));
        assertThat(customerRepository.findByName(otherCustomer.getName()).orElseThrow(), samePropertyValuesAs(otherCustomer));
        assertThat(customerRepository.findByEmail(blockedCustomer.getEmail()).orElseThrow(), samePropertyValuesAs((blockedCustomer)));
    }

    @Test
    @DisplayName("등록한 유저와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Customer customer = createCustomer(UUID.randomUUID());
        Customer otherCustomer = createOtherCustomer(UUID.randomUUID());

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);

        assertThat(customerRepository.findAll().isEmpty(), is(false));
        assertThat(customerRepository.findAll(), hasSize(2));
    }


    @Test
    @DisplayName("유저를 등록한 후에 필드를 변경할 수 있다.")
    void testUpdate() {
        Customer customer = createCustomer(UUID.randomUUID());
        customerRepository.save(customer);

        Customer update = createOtherCustomer(customer.getCustomerId());
        customerRepository.update(update);

        assertThat(customer, not(samePropertyValuesAs(update)));
    }

    @Test
    @DisplayName("전체 유저를 삭제할 수 있다.")
    void testDeleteAll() {
        Customer customer = createCustomer(UUID.randomUUID());
        Customer otherCustomer = createOtherCustomer(UUID.randomUUID());
        Customer blockedCustomer = createBlockedCustomer(UUID.randomUUID());

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);
        customerRepository.save(blockedCustomer);
        customerRepository.deleteAll();

        assertThat(customerRepository.findAll().isEmpty(), is(true));
    }

    private Customer createCustomer(UUID customerId) {
        return Customer.builder()
                .customerId((customerId != null) ? customerId : UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
    }

    private Customer createOtherCustomer(UUID customerId) {
        return Customer.builder()
                .customerId((customerId != null) ? customerId : UUID.randomUUID())
                .name("other-test")
                .email("other-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
    }

    private Customer createBlockedCustomer(UUID customerId) {
        return Customer.builder()
                .customerId((customerId != null) ? customerId : UUID.randomUUID())
                .name("another-test")
                .email("another-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(true)
                .build();
    }
}