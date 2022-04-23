package org.prgrms.kdt.shop.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

class CustomerTest {
    @Test
    @DisplayName("로그인 테스트")
    void login( ) {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "hunki", "gnsrl76@naver.com", LocalDateTime.now());
        var lastLoginAt = customer.getLastLoginAt();
        //when
        customer.login();
        //then
        assertThat(customer.getLastLoginAt(), notNullValue());
    }

    @Test
    @DisplayName("이름 입력 검증 테스트(공백입력)")
    void validateName( ) {
        //then
        try {
            new Customer(UUID.randomUUID(), "", "gnsrl76@naver.com", LocalDateTime.now());
            fail();
        } catch (RuntimeException e) {
        }

    }
}