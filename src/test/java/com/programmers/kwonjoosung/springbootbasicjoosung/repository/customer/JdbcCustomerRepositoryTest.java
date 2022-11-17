package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.config.DataSourceConfig;
import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongFindDataException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@SpringJUnitConfig
@Import(DataSourceConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class JdbcCustomerRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeEach
    void setUp() {
        this.jdbcCustomerRepository = new JdbcCustomerRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("[성공] 고객을 저장할 수 있다.")
    void insertCustomerTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(),"test1");
        //when
        jdbcCustomerRepository.insert(customer);
        //then
        assertThat(jdbcCustomerRepository.findById(customer.getCustomerId())).isEqualTo(customer);
    }

    @Test
    @DisplayName("[실패] 동일한 고객은 저장할 수 없다.")
    void insertSameCustomerTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(),"test2");
        //when
        jdbcCustomerRepository.insert(customer);
        //then
        assertThatExceptionOfType(WrongFindDataException.class)
                .isThrownBy(() -> jdbcCustomerRepository.insert(customer))
                .withMessage("이미 존재하는 ID입니다.");
    }

    @Test
    @DisplayName("[성공] 고객을 조회할 수 있다.")
    void findByCustomerIdTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test3");
        //when
        jdbcCustomerRepository.insert(customer);
        //then
        assertThat(jdbcCustomerRepository.findById(customer.getCustomerId())).isEqualTo(customer);
    }

    @Test
    @DisplayName("[실패] 없는 고객은 조회할 수 없다.")
    void findByNotExistIdTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test4");
        //when & then
        assertThatExceptionOfType(WrongFindDataException.class)
                .isThrownBy(() -> jdbcCustomerRepository.findById(customer.getCustomerId()))
                .withMessage("해당 ID는 존재하지 않는 ID입니다.");
    }

    @Test
    @DisplayName("[성공] 모든 고객을 조회할 수 있다.")
    void findAllTest() {
        //given
        Customer customer1 = new Customer(UUID.randomUUID(),"test5");
        Customer customer2 = new Customer(UUID.randomUUID(),"test6");
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);
        //when
        List<Customer> customers = jdbcCustomerRepository.findAll();
        //then
        assertThat(customers).contains(customer1, customer2);
    }

    @Test
    @DisplayName("[실패] 테이블이 비어 있으면 고객을 조회할 수 없다.")
    void findAllFromEmptyTableTest() {
        //when
        List<Customer> all = jdbcCustomerRepository.findAll();
        //then
        assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("[성공] 고객 이름을 변경할 수 있다.")
    void updateCustomerNameTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test7");
        jdbcCustomerRepository.insert(customer);
        Customer newCustomer = new Customer(customer.getCustomerId(), "test8");
        //when
        jdbcCustomerRepository.update(newCustomer);
        //then
        assertThat(jdbcCustomerRepository.findById(customer.getCustomerId())).isEqualTo(newCustomer);
        assertThat(jdbcCustomerRepository.findById(customer.getCustomerId()).getName()).isEqualTo(newCustomer.getName());
    }

    @Test
    @DisplayName("[실패] 없는 고객은 이름을 변경할 수 없다.")
    void updateNotExistCustomerNameTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test9");
        //when & then
        assertThatExceptionOfType(WrongFindDataException.class)
                .isThrownBy(() -> jdbcCustomerRepository.update(customer))
                .withMessage("해당 ID는 존재하지 않는 ID입니다.");
    }

    @Test
    @DisplayName("[성공] 고객을 삭제할 수 있다.")
    void deleteCustomerTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test10");
        jdbcCustomerRepository.insert(customer);
        //when
        boolean result = jdbcCustomerRepository.delete(customer.getCustomerId());
        //then
        assertThat(result).isTrue();
        assertThatExceptionOfType(WrongFindDataException.class)
                .isThrownBy(() -> jdbcCustomerRepository.findById(customer.getCustomerId()))
                .withMessage("해당 ID는 존재하지 않는 ID입니다.");
    }

    @Test
    @DisplayName("[실패] 없는 고객은 삭제할 수 없다.")
    void deleteNotExistCustomerTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test11");
        //when
        boolean result = jdbcCustomerRepository.delete(customer.getCustomerId());
        //then
        assertThat(result).isFalse();
    }

}