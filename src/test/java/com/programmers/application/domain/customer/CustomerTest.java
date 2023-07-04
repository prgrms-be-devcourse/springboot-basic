package com.programmers.application.domain.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("옳바른 인자들로 of()를 실행하면 Customer가 생성된다.")
    @ParameterizedTest
    @CsvSource(value = {
            "aCustomer, mgtmh991013@naver.com",
            "bCustomer, mgtmh991013@gmail.com"
    })
    void createCustomer(String name, String email) {
        //given
        UUID customerId = UUID.randomUUID();
        LocalDateTime lastLoginAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();

        //when
        Customer customer = Customer.of(customerId, name, email, lastLoginAt, createdAt);

        //then
        assertThat(customer).isNotNull();
    }

    @DisplayName("고객 아이디가 없다면, of()를 실행할 때 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    void throwNullCustomerIdException(UUID customerId) {
        //given
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        LocalDateTime lastLoginAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 10, 1, 12);

        //when, then
        Assertions.assertThatThrownBy(() -> Customer.of(customerId, name, email, lastLoginAt, createdAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 아이디가 비어있습니다.");
    }

    @DisplayName("이메일 형식이 맞지 않으면, of()를 실행할 때 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = "mgtmh991013naver.com, mgtmh991013gmail.com")
    void throwIncorrectEmailFormatException(String email) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        LocalDateTime lastLoginAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();

        //when, then
        assertThatThrownBy(() -> Customer.of(customerId, name, email, lastLoginAt, createdAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("옳바른 이메일 형식을 입력해주세요. 입력값: %s", email));
    }

    @DisplayName("Customer가 주어졌을 때, login()을 실행하면, lastLoginAt 값이 현재 시간으로 변경된다.")
    @Test
    void login() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        LocalDateTime lastLoginAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        Customer customer = Customer.of(customerId, name, email, lastLoginAt, createdAt);

        //when
        customer.login();

        //then
        assertThat(customer.getLastLoginAt())
                .isNotEqualTo(lastLoginAt)
                .isAfter(LocalDateTime.now().minusSeconds(1))
                .isBefore(LocalDateTime.now().plusSeconds(1));
    }

    @DisplayName("Customer가 주어졌을 때, changeName()을 실행하면, name 값이 변경된다.")
    @ParameterizedTest
    @ValueSource(strings = "bCustomer, cCustomer")
    void changeName(String changeName) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        LocalDateTime lastLoginAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        Customer customer = Customer.of(customerId, name, email, lastLoginAt, createdAt);

        //when
        customer.changeName(changeName);

        //then
        assertThat(customer.getName()).isEqualTo(changeName);
    }

    @DisplayName("변경 할 이름이 비어있거나 없으면, changeName()을 실행할 때 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwNullAndEmptyNameException(String changeName) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        LocalDateTime lastLoginAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 10, 1, 12);
        Customer customer = Customer.of(customerId, name, email, lastLoginAt, createdAt);

        //when, then
        Assertions.assertThatThrownBy(() -> customer.changeName(changeName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 이름이 비었습니다.");
    }
}
