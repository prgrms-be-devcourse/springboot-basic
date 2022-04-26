package org.prgrms.kdtspringdemo.domain.customer.service;

import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.testcontainers.AbstractContainerDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest extends AbstractContainerDatabaseTest {


    @Autowired
    private CustomerRepository customerRepository;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
    }

    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        customerRepository.insert(newCustomer);

        Optional<Customer> receiveCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(receiveCustomer.isEmpty(), is(false));
        assertThat(receiveCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("repository 사이즈가 0 일때 count 테스트")
    public void zeroCountTest() throws Exception {
        // given

        // when
        int count = customerService.count();

        // then
        assertThat(count, is(0));

    }

    @Test
    @DisplayName("맞는 아이디로 Customer 를 찾는 경우 테스트")
    public void findByIdTest() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "test-user", "test-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        Optional<Customer> idCustomer = customerService.findById(id);

        // then
        assertThat(customer, samePropertyValuesAs(idCustomer));

    }

    @Test
    @DisplayName("잘못된 아이디로 Customer 를 찾는 경우 테스트")
    public void findByIdTestError() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "test-user", "test-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        UUID wrongId = UUID.randomUUID();
        Optional<Customer> idCustomer = customerService.findById(wrongId);

        // then
        assertThat(customer, samePropertyValuesAs(Optional.of(null)));
    }
}