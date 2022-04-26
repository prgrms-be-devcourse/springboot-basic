package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.TestConfiguration;
import org.prgrms.kdt.model.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        TestConfiguration.clean(embeddedMysql);
    }
    @AfterEach
    void clean() {
        customerJdbcRepository.deleteAllCustomer();
    }

    @Test
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        customerJdbcRepository.insertCustomer(newCustomer);

        Optional<Customer> receiveCustomer = customerJdbcRepository.findCustomerById(newCustomer.getCustomerId());
        assertThat(receiveCustomer.isEmpty(), is(false));
        assertThat(receiveCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("이메일이 중복된 고객은 추가 할 수 없다.")
    void testInsertDuplicateCustomer() {
        //given
        Customer newCustomer2 = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerJdbcRepository.insertCustomer(newCustomer2);
        assertThrows(DuplicateKeyException.class, () -> customerJdbcRepository.insertCustomer(newCustomer2));
    }

    @Test
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void testFindAll() {
        Customer newCustomer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerJdbcRepository.insertCustomer(newCustomer);
        customerJdbcRepository.insertCustomer(newCustomer2);
        var customers = customerJdbcRepository.findAllCustomer();
        assertThat(customers.isEmptyList(), is(false));
    }

    @Test
    @DisplayName("아이디로 고객을 조회 할 수 있다.")
    void testFindById() {
        customerJdbcRepository.insertCustomer(newCustomer);
        var customerId = newCustomer.getCustomerId();
        Optional<Customer> receiveCustomer = customerJdbcRepository.findCustomerById(customerId);

        assertThat(receiveCustomer, notNullValue());
        assertThat(receiveCustomer.get().getCustomerId(), equalTo(customerId));
    }
}