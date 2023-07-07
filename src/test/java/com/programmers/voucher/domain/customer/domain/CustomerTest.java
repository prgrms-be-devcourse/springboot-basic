package com.programmers.voucher.domain.customer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    @DisplayName("성공: customer 생성")
    void newCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        String email = "customer@gmail.com";
        String name = "customer";

        //when
        Customer customer = new Customer(customerId, email, name);

        //then
        assertThat(customer.getCustomerId()).isEqualTo(customerId);
        assertThat(customer).extracting("email").isEqualTo(email);
        assertThat(customer).extracting("name").isEqualTo(name);
    }

    @Test
    @DisplayName("예외: customer 생성 - 사용자 이름이 20자를 초과한 이메일")
    void newCustomer_ButInvalidEmail_UsernameOutOfRange_Then_Exception() {
        //given
        UUID customerId = UUID.randomUUID();
        String email = "anUsernameUpTo20Chars@gmail.com";
        String name = "customer";

        //when
        //then
        assertThatThrownBy(() -> new Customer(customerId, email, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: customer 생성 - 도메인 이름이 20자를 초과한 이메일")
    void newCustomer_ButInvalidEmail_DomainOutOfRange_Then_Exception() {
        //given
        UUID customerId = UUID.randomUUID();
        String email = "customer@domainNameUpto20Chars.com";
        String name = "customer";

        //when
        //then
        assertThatThrownBy(() -> new Customer(customerId, email, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "한글@gmail.com", "customer!@gmail.com", "customer;@gmail.com"
    })
    @DisplayName("예외: customer 생성 - 잘못된 형식의 이메일")
    void newCustomer_ButInvalidEmail_InvalidCharacter_Then_Exception(String email) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "customer";

        //when
        //then
        assertThatThrownBy(() -> new Customer(customerId, email, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: customer 생성 - 20자를 초과한 이름")
    void newCustomer_ButInvalidName_OutOfRange_Then_Exception() {
        //given
        UUID customerId = UUID.randomUUID();
        String email = "customer@gmail.com";
        String name = "anUsernameUpTo20Chars";

        //when
        //then
        assertThatThrownBy(() -> new Customer(customerId, email, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "한글", "customer++", "customer.!"
    })
    @DisplayName("예외: customer 생성 - 잘못된 형식의 이름")
    void newCustomer_ButInvalidName_InvalidCharacter_Then_Exception(String name) {
        //given
        UUID customerId = UUID.randomUUID();
        String email = "customer@gmail.com";

        //when
        //then
        assertThatThrownBy(() -> new Customer(customerId, email, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: customer 수정 - 20자를 초과한 이름")
    void updateCustomer_ButInvalidName_OutOfRange_Then_Exception() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");

        //when
        //then
        String name = "anUsernameUpTo20Chars";
        assertThatThrownBy(() -> customer.update(name, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "한글", "customer++", "customer.!"
    })
    @DisplayName("예외: customer 수정 - 잘못된 형식의 이름")
    void updateCustomer_ButInvalidName_InvalidCharacter_Then_Exception(String name) {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");

        //when
        //then
        assertThatThrownBy(() -> customer.update(name, false))
                .isInstanceOf(IllegalArgumentException.class);
    }
}