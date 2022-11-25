package com.programmers.customer.service;

import com.programmers.customer.dto.CustomerDto;
import com.programmers.customer.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;


    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("정상 회원은 성공적으로 등록된다.")
    void 회원가입_성공() {
        assertDoesNotThrow(() -> customerService.join("test1", "test@test.com"));
    }

    @Test
    @Transactional
    @DisplayName("중복 email 회원은 가입이 불가능하다.")
    void 중복이메일_가입_실패() {
        assertDoesNotThrow(() -> customerService.join("tester1", "test@test.com"));

        assertThrows(RuntimeException.class, () -> customerService.join("hello", "test@test.com"));
    }

    @Test
    @Transactional
    @DisplayName("등록된 회원의 이름으로 조회 시 성공")
    void 회원_조회_이름() {
        CustomerDto customer = customerService.join("tester1", "test@test.com");

        CustomerDto findOne = customerService.findByName(customer.getName());

        assertEquals(customer.getName(), findOne.getName());
    }

    @Test
    @Transactional
    @DisplayName("등록된 회원의 Id로 조회 시 성공")
    void 회원_조회_Id() {
        CustomerDto customer = customerService.join("tester1", "test@test.com");

        CustomerDto findOne = customerService.findById(customer.getCustomerId());
        assertEquals(customer.getCustomerId(), findOne.getCustomerId());
    }

    @Test
    @Transactional
    @DisplayName("등록된 회원의 Email로 조회 시 성공")
    void 회원_조회_Email() {
        CustomerDto customer = customerService.join("tester1", "test@test.com");

        CustomerDto findOne = customerService.findByEmail(customer.getEmail());
        assertEquals(customer.getEmail(), findOne.getEmail());
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
        customerService.join("tester1", "test1@test.com");
        customerService.join("tester2", "test2@test.com");

        List<CustomerDto> customers = customerService.findAll();
        assertThat(customers, hasSize(2));
    }
}