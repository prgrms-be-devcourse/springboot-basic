package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DbCustomerRepositoryTest {

    @Autowired
    DbCustomerRepository repository;

    @Test
    @DisplayName("저장 시 고객이 성공적으로 저장되어야 한다.")
    void 회원_DB_저장_테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "aaa@aaa.com", LocalDateTime.now());
        assertDoesNotThrow(() -> repository.insert(customer));
    }

    @Test
    @DisplayName("고객 정보를 수정할 수 있다.")
    void 회원_DB_update_테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "aaa@aaa.com", LocalDateTime.now());
        repository.insert(customer);

        Optional<Customer> beforeResult = repository.findById(customer.getCustomerId());
        assertTrue(beforeResult.isPresent());
        assertEquals(customer, beforeResult.get());

        customer.changeName("giseo");
        repository.update(customer);

        Optional<Customer> afterResult = repository.findById(customer.getCustomerId());
        assertTrue(afterResult.isPresent());
        assertEquals(customer, afterResult.get());
        assertNotEquals(beforeResult.get(), afterResult.get());
    }

    @Test
    @DisplayName("count 쿼리의 결과는 DB에 저장된 고객의 수와 동일하다.")
    void 회원_DB_Count_테스트() {
        Customer customer1 = new Customer(UUID.randomUUID(), "kim", "aaa@aaa.com", LocalDateTime.now());
        repository.insert(customer1);
        assertThat(repository.count(), is(1));

        Customer customer2 = new Customer(UUID.randomUUID(), "lee", "bbb@bbb.com", LocalDateTime.now());
        repository.insert(customer2);
        assertThat(repository.count(), is(2));
    }

    @Test
    @DisplayName("회원 ID로 조회 시 고객이 성공적으로 조회된다.")
    void 회원ID로_DB_조회_테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "aaa@aaa.com", LocalDateTime.now());
        repository.insert(customer);

        Optional<Customer> result = repository.findById(customer.getCustomerId());
        assertTrue(result.isPresent());

        Customer findOne = result.get();
        assertEquals(customer, findOne);
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    void 회원이메일로_DB_조회테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "aaaa@aaa.com", LocalDateTime.now());
        repository.insert(customer);

        Optional<Customer> result = repository.findByEmail(customer.getEmail());
        assertThat(result.isEmpty(), is(false));

        Optional<Customer> unKnown = repository.findByEmail("unknown@aaa.com");
        assertThat(unKnown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    void 회원이름으로_DB_조회테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "aaa@aaa.com", LocalDateTime.now());
        repository.insert(customer);

        Optional<Customer> result = repository.findByName(customer.getName());
        assertThat(result.isEmpty(), is(false));

        Optional<Customer> unKnown = repository.findByName("unknown");
        assertThat(unKnown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "aaa@aaa.com", LocalDateTime.now());
        repository.insert(customer);

        List<Customer> customers = repository.findAll();
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasSize(1));
    }
}