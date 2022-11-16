package com.programmers.customer.service;

import com.programmers.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @Test
    @Transactional
    @DisplayName("정상 회원은 성공적으로 등록된다.")
    void 회원가입_성공() {
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "test@test.com", LocalDateTime.now());
        assertDoesNotThrow(() -> customerService.join(customer));
    }

    @Test
    @Transactional
    @DisplayName("중복 email 회원은 가입이 불가능하다.")
    void 중복이메일_가입_실패() {
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "test@test.com", LocalDateTime.now());
        assertDoesNotThrow(() -> customerService.join(customer));

        Customer sameEmailUser = new Customer(UUID.randomUUID(), "hello", "test@test.com", LocalDateTime.now());
        assertThrows(RuntimeException.class, () -> customerService.join(sameEmailUser));
    }

    @Test
    @Transactional
    @DisplayName("회원의 이름 정보를 변경할 수 있다.")
    void 회원정보_업데이트() {
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "test@test.com", LocalDateTime.now());
        customerService.join(customer);

        customer.changeName("tester2");
        Customer updateCustomer = customerService.update(customer);

        assertThat(updateCustomer.getName(), is("tester2"));
        assertDoesNotThrow(() -> customerService.findByName("tester2"));
    }

    @Test
    @Transactional
    @DisplayName("등록된 회원의 이름으로 조회 시 성공")
    void 회원_조회_이름() {
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "test@test.com", LocalDateTime.now());
        customerService.join(customer);

        Customer findOne = customerService.findByName(customer.getName());
        assertEquals(customer, findOne);
    }

    @Test
    @Transactional
    @DisplayName("등록된 회원의 Id로 조회 시 성공")
    void 회원_조회_Id() {
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "test@test.com", LocalDateTime.now());
        customerService.join(customer);

        Customer findOne = customerService.findById(customer.getCustomerId());
        assertEquals(customer, findOne);
    }

    @Test
    @Transactional
    @DisplayName("등록된 회원의 Email로 조회 시 성공")
    void 회원_조회_Email() {
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "test@test.com", LocalDateTime.now());
        customerService.join(customer);

        Customer findOne = customerService.findByEmail(customer.getEmail());
        assertEquals(customer, findOne);
    }

    @Test
    @Transactional
    @DisplayName("미등록된 회원의 이름으로 조회 시 실패")
    void 비회원_조회_실패_이름() {
        assertThrows(RuntimeException.class, () -> customerService.findByName("unKnown"));
    }

    @Test
    @Transactional
    @DisplayName("미등록된 회원의 Id로 조회 시 실패")
    void 비회원_조회_실패_Id() {
        assertThrows(RuntimeException.class, () -> customerService.findById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @DisplayName("미등록된 회원의 Email로 조회 시 실패")
    void 비회원_조회_실패_Email() {
        assertThrows(RuntimeException.class, () -> customerService.findByEmail("unknown@naver.com"));
    }

    @Test
    @DisplayName("저장된 회원 목록을 조회할 수 있다")
    @Transactional
    void 전체_회원_조회() {
        Customer customer1 = new Customer(UUID.randomUUID(), "tester1", "test1@test.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "tester2", "test2@test.com", LocalDateTime.now());
        customerService.join(customer1);
        customerService.join(customer2);

        List<Customer> customers = customerService.findAll();
        assertThat(customers, hasSize(2));
    }
}