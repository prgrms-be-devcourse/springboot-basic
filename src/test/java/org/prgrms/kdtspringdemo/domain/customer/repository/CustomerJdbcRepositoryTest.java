package org.prgrms.kdtspringdemo.domain.customer.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringdemo.testcontainers.AbstractContainerDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest extends AbstractContainerDatabaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer newCustomer;

    @BeforeAll
    void beforeAll() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());
    }

    @Test
    @DisplayName("고객을 추가 할 수 있다.")
    @Order(1)
    void testInsert() {
        customerRepository.insert(newCustomer);
        System.out.println(newCustomer);

        Optional<Customer> receiveCustomer = customerRepository.findById(newCustomer.getCustomerId());
        System.out.println(receiveCustomer);
        assertThat(receiveCustomer.isEmpty(), is(false));
        assertThat(receiveCustomer.get(), samePropertyValuesAs(newCustomer));
    }


    @Test
    @Order(2)
    @DisplayName("고객을 새로운 고객으로 업데이트 한다.")
    public void updateTest () throws Exception{
        // given
        Customer updatedCustomer = new Customer(UUID.randomUUID(), "update-user", "update-user@gmail.com", LocalDateTime.now());

        // when
        Customer update = customerRepository.update(updatedCustomer);

        // then
        assertThat(updatedCustomer, samePropertyValuesAs(update));
    }

    @Test
    @Order(3)
    @DisplayName("repository count 를 한다.")
    public void countTest () throws Exception{
        // given
        int count = customerRepository.count();

        // when

        // then
        assertThat(count, is(1));
    }

    @Test
    @Order(4)
    @DisplayName("모든 customer 를 찾아온다.")
    public void findAllTest () throws Exception{
        // given
        Customer secondCustomer = new Customer(UUID.randomUUID(), "second-user", "second-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(secondCustomer);

        // when
        List<Customer> customers = customerRepository.findAll();

        // then
        assertThat(customers.size(), is(customerRepository.count()));
        assertThat(customers.get(1), samePropertyValuesAs(secondCustomer));
    }

    @Test
    @Order(5)
    @DisplayName("id 를 통해 customer 를 찾아온다.")
    public void findByIdTest () throws Exception{
        // given
        UUID id = UUID.randomUUID();
        Customer idCustomer = new Customer(id, "id-user", "id-user@gmail.com", LocalDateTime.now());

        // when
        customerRepository.insert(idCustomer);

        // then
        assertThat(idCustomer, samePropertyValuesAs(customerRepository.findById(id)));
    }

    @Test
    @Order(6)
    @DisplayName("name 를 통해 customer 를 찾아온다.")
    public void findByName () throws Exception{
        // given
        String name = "name-user";
        Customer nameCustomer = new Customer(UUID.randomUUID(), name, "id-user@gmail.com", LocalDateTime.now());

        // when
        customerRepository.insert(nameCustomer);

        // then
        assertThat(nameCustomer, samePropertyValuesAs(customerRepository.findByName("name-user")));
    }

    @Test
    @Order(7)
    @DisplayName("email 를 통해 customer 를 찾아온다.")
    public void findByEmail () throws Exception{
        // given
        String email = "email-user@gamail.com";
        Customer emailCustomer = new Customer(UUID.randomUUID(), "email-name", email, LocalDateTime.now());

        // when
        customerRepository.insert(emailCustomer);

        // then
        assertThat(emailCustomer, samePropertyValuesAs(customerRepository.findByEmail("email-user@gamail.com")));
    }

    @Test
    @Order(8)
    @DisplayName("repository 를 제거한다.")
    public void DeleteAll () throws Exception{
        // given

        // when
        customerRepository.deleteAll();

        // then
        assertThat(customerRepository.count(), is(0));
    }
}