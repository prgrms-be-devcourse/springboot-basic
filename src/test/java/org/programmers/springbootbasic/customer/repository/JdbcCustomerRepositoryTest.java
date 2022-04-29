package org.programmers.springbootbasic.customer.repository;

import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.programmers.springbootbasic.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository jdbcCustomerRepository;

    @BeforeAll
    void setup() {
        dbSetup();
    }

    @AfterEach
    void cleanup() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);
        var retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());

        //then
        assertThat(retrievedCustomer).isNotEmpty().contains(customer);
    }

    @Test
    @DisplayName("중복된 고객을 추가 할 수 없다.")
    void testDuplicateCustomerInsert() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);

        //then
        assertThatThrownBy(() -> jdbcCustomerRepository.insert(customer))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void testFindAll() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);
        var customers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("이름으로 고객을 조회 할 수 있다.")
    void testFindByName() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);
        var customers = jdbcCustomerRepository.findByName(customer.getCustomerName());

        //then
        assertThat(customers).isPresent();
    }

    @Test
    @DisplayName("없는 이름으로 고객을 조회 할 수 없다.")
    void testFindByNonExistentName() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        var unknown = jdbcCustomerRepository.findByName("unknown-user");

        //then
        assertThat(unknown).isNotPresent();
    }

    @Test
    @DisplayName("아이디로 고객을 조회 할 수 있다.")
    void testFindById() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);
        var customers = jdbcCustomerRepository.findById(customer.getCustomerId());

        //then
        assertThat(customers).isPresent();
    }

    @Test
    @DisplayName("없는 아이디로 고객을 조회 할 수 없다.")
    void testFindByNonExistentId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        var unknown = jdbcCustomerRepository.findById(UUID.randomUUID());

        //then
        assertThat(unknown).isNotPresent();
    }

    @Test
    @DisplayName("아이디로 고객을 삭제 할 수 있다.")
    void testDeleteById() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);
        jdbcCustomerRepository.deleteById(customer.getCustomerId());
        var optionalCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());

        //then
        assertThat(optionalCustomer).isPresent();
    }

    @Test
    @DisplayName("모든 고객을 삭제 할 수 있다.")
    void testDeleteAll() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "new-customer2", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "new-customer3", LocalDateTime.now());

        //when
        jdbcCustomerRepository.insert(customer);
        jdbcCustomerRepository.insert(customer2);
        jdbcCustomerRepository.insert(customer3);
        jdbcCustomerRepository.deleteAll();
        var all = jdbcCustomerRepository.findAll();

        //then
        assertThat(all).isEmpty();
    }
}
