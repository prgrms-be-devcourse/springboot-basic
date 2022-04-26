package org.prgrms.kdtspringdemo.domain.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.TestConfiguration;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    CustomerJdbcRepository customerRepository;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        TestConfiguration.clean(embeddedMysql);
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


    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    @DisplayName("고객을 저장하면 반환되는 객체는 저장한 고객과 같아야 하고 레포지토리의 사이즈는 1이 되어야 한다.")
    public void testSave() {
        Customer insertedCustomer = customerRepository.insert(newCustomer);

        assertThat(insertedCustomer, notNullValue());
        assertThat(insertedCustomer, samePropertyValuesAs(newCustomer));
        assertThat(customerRepository.count(), is(1));
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
    @Order(2)
    @DisplayName("repository count 를 한다.")
    public void countTest () throws Exception{
        // given
        int count = customerRepository.count();

        // when

        // then
        assertThat(count, is(1));
    }

    @Test
    @Order(3)
    @DisplayName("모든 customer 를 찾아온다.")
    public void findAllTest () throws Exception{
        // given
        Customer secondCustomer = new Customer(UUID.randomUUID(), "second-user", "second-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(secondCustomer);

        // when
        List<Customer> all = customerRepository.findAll();

        // then
        assertThat(all.size(), is(customerRepository.count()));
        assertThat(all.get(1), samePropertyValuesAs(secondCustomer));
    }

    @Test
    @Order(4)
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
    @Order(5)
    @DisplayName("name 를 통해 customer 를 찾아온다.")
    public void findByName () throws Exception{
        // given
        String name = "name-user";
        Customer nameCustomer = new Customer(UUID.randomUUID(), name, "id-user@gmail.com", LocalDateTime.now());

        // when
        customerRepository.insert(nameCustomer);

        // then
        assertThat(nameCustomer, samePropertyValuesAs(customerRepository.findByName(name)));
    }

    @Test
    @Order(6)
    @DisplayName("email 를 통해 customer 를 찾아온다.")
    public void findByEmail () throws Exception{
        // given
        String email = "email-user@gamail.com";
        Customer emailCustomer = new Customer(UUID.randomUUID(), "email-name", email, LocalDateTime.now());

        // when
        customerRepository.insert(emailCustomer);

        // then
        assertThat(emailCustomer, samePropertyValuesAs(customerRepository.findByEmail(email)));
    }

    @Test
    @Order(7)
    @DisplayName("id 를 통해 customer 를 찾아온다.")
    public void DeleteAll () throws Exception{
        // given

        // when
        customerRepository.deleteAll();

        // then
        assertThat(customerRepository.count(), is(0));
    }

}