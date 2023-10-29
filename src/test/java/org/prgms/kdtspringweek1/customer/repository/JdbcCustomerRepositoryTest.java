package org.prgms.kdtspringweek1.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
class JdbcCustomerRepositoryTest {

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.update("DELETE FROM wallets", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Test
    @DisplayName("고객 저장 성공")
    void Success_Save() {
        // given
        Customer customer = Customer.createWithName("최정은");
        int beforeSave = jdbcCustomerRepository.findAllCustomers().size();

        // when
        Customer savedCustomer = jdbcCustomerRepository.save(customer);

        // then
        assertThat(savedCustomer, samePropertyValuesAs(customer));
        assertThat(jdbcCustomerRepository.findAllCustomers(), hasSize(beforeSave + 1));
    }

    @Test
    @DisplayName("모든 고객 검색 성공")
    void Success_FindAllCustomers() {
        // given
        List<Customer> savedCustomers = saveCustomers();
        int customerCnt = jdbcCustomerRepository.findAllCustomers().size();

        // when
        List<Customer> allCustomers = jdbcCustomerRepository.findAllCustomers();

        // then
        assertThat(allCustomers, hasSize(customerCnt));
    }

    @Test
    @DisplayName("고객 아이디로 고객 검색 성공")
    void Success_FindById() {
        // given
        Customer customer = Customer.createWithName("최정은");
        jdbcCustomerRepository.save(customer);

        // when
        Optional<Customer> foundCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());

        // then
        assertThat(foundCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객 정보 수정 성공")
    void Success_Update() {
        // given
        Customer customer = Customer.createWithName("최정은");
        jdbcCustomerRepository.save(customer);
        Customer customerToUpdate = Customer.createWithIdAndNameAndIsBlackCustomer(customer.getCustomerId(), "ChoiJeongeun", false);

        // when
        Customer updatedCustomer = jdbcCustomerRepository.update(customerToUpdate);

        // then
        assertThat(updatedCustomer, samePropertyValuesAs(customerToUpdate));
    }

    @Test
    @DisplayName("모든 고객 삭제 성공")
    void Success_DeleteAll() {
        // given
        saveCustomers();

        // when
        jdbcCustomerRepository.deleteAll();

        // then
        assertThat(jdbcCustomerRepository.findAllCustomers(), hasSize(0));
    }

    @Test
    @DisplayName("고객 아이디로 고객 삭제 성공")
    void Success_DeleteById() {
        // given
        Customer customer = Customer.createWithName("최정은");
        jdbcCustomerRepository.save(customer);

        // when
        jdbcCustomerRepository.deleteById(customer.getCustomerId());

        // then
        assertTrue(jdbcCustomerRepository.findById(customer.getCustomerId()).isEmpty());
    }

    @Test
    @DisplayName("모든 블랙 고객 검색 성공")
    void Success_FindAllBlackCustomer() {
        // given
        List<Customer> notBlackConsumers = saveNotBlackCustomers();
        List<Customer> blackConsumers = saveBlackCustomers();

        // when
        List<Customer> allBlackConsumer = jdbcCustomerRepository.findAllBlackCustomer();

        // then
        assertThat(allBlackConsumer, hasSize(blackConsumers.size()));
    }

    private List<Customer> saveCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            customers.add(jdbcCustomerRepository.save(Customer.createWithNameAndIsBlackCustomer("최정은" + i, new Random().nextBoolean())));
        }

        return customers;
    }

    private List<Customer> saveNotBlackCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            customers.add(jdbcCustomerRepository.save(Customer.createWithNameAndIsBlackCustomer("최정은" + i, false)));
        }

        return customers;
    }

    private List<Customer> saveBlackCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            customers.add(jdbcCustomerRepository.save(Customer.createWithNameAndIsBlackCustomer("최정은" + i, true)));
        }

        return customers;
    }
}