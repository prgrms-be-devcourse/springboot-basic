package org.prgrms.java.repository.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

class MemoryCustomerRepositoryTest {
    private static final CustomerRepository CUSTOMER_REPOSITORY = new MemoryCustomerRepository();

    @BeforeEach
    @AfterEach
    void clean() {
        CUSTOMER_REPOSITORY.deleteAll();
    }

    @Test
    @DisplayName("정상/블랙 유저를 등록할 수 있다.")
    void testInsert() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        Customer insertedCustomer = CUSTOMER_REPOSITORY.insert(customer);
        Customer insertedBlockedCustomer = CUSTOMER_REPOSITORY.insert(blockedCustomer);

        assertThat(customer, samePropertyValuesAs(insertedCustomer));
        assertThat(blockedCustomer, samePropertyValuesAs(insertedBlockedCustomer));
    }

    @Test
    @DisplayName("동일한 ID의 유저는 등록할 수 없다.")
    void testInsertSameIdCustomer() {
        assertThrows(CustomerException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(customerId, "test", "test@gmail.com");
            Customer customer2 = new Customer(customerId, "test2", "test2@gmail.com");

            CUSTOMER_REPOSITORY.insert(customer);
            CUSTOMER_REPOSITORY.insert(customer2);
        });
    }

    @Test
    @DisplayName("등록한 유저가 정상적으로 반환돼야 한다.")
    void testFindById() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com");

        CUSTOMER_REPOSITORY.insert(customer);
        CUSTOMER_REPOSITORY.insert(customer2);

        assertThat(CUSTOMER_REPOSITORY.findById(customer.getCustomerId(), customer.isBlocked()).get(), samePropertyValuesAs(customer));
        assertThat(CUSTOMER_REPOSITORY.findById(customer2.getCustomerId(), customer2.isBlocked()).get(), samePropertyValuesAs(customer2));
        assertThat(CUSTOMER_REPOSITORY.findById(customer.getCustomerId(), customer.isBlocked()).get(), not(samePropertyValuesAs((customer2))));
    }

    @Test
    @DisplayName("등록한 유저와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com");

        CUSTOMER_REPOSITORY.insert(customer);
        CUSTOMER_REPOSITORY.insert(customer2);

        assertThat(CUSTOMER_REPOSITORY.findAll(customer.isBlocked()).isEmpty(), is(false));
        assertThat(CUSTOMER_REPOSITORY.findAll(customer2.isBlocked()), hasSize(2));
    }

    @Test
    @DisplayName("등록한 유저와 전체 삭제한 개수가 같다.")
    void testDeleteAll() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com");
        Customer blackCustomer = new Customer(UUID.randomUUID(), "unknown", "spam@spam.com", true);

        CUSTOMER_REPOSITORY.insert(customer);
        CUSTOMER_REPOSITORY.insert(customer2);
        CUSTOMER_REPOSITORY.insert(blackCustomer);

        assertThat(CUSTOMER_REPOSITORY.deleteAll(), is(3L));
    }
}