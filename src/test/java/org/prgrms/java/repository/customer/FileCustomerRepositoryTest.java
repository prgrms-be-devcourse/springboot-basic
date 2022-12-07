package org.prgrms.java.repository.customer;

import org.junit.jupiter.api.*;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileCustomerRepositoryTest {
    private static final CustomerRepository customerRepository = new FileCustomerRepository("data", "customer_test.csv", "customer_blacklist_test.csv");

    @BeforeEach
    void clean() { cleanup(); }

    @AfterAll
    static void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("정상/블랙 유저를 파일로 등록할 수 있다.")
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
    @DisplayName("등록한 유저가 정상적으로 반환돼야 한다.")
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

        customerRepository.save(customer);
        customerRepository.save(otherCustomer);

        assertThat(customerRepository.findById(customer.getCustomerId()).get(), samePropertyValuesAs(customer));
        assertThat(customerRepository.findById(otherCustomer.getCustomerId()).get(), samePropertyValuesAs(otherCustomer));
        assertThat(customerRepository.findById(customer.getCustomerId()).get(), not(samePropertyValuesAs((otherCustomer))));
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