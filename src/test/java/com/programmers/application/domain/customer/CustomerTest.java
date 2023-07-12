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

    @DisplayName("옳바른 인자들로 Customer 생성자 호출하면 고객이 생성된다.")
    @ParameterizedTest
    @CsvSource(value = {
            "aCustomer, mgtmh991013@naver.com",
            "bCustomer, mgtmh991013@gmail.com"
    })
    void createCustomer(String name, String email) {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        Customer customer = new Customer(customerId, name, email);

        //then
        assertThat(customer).isNotNull();
    }

    @DisplayName("Customer가 주어졌을 때, login()을 실행하면, lastLoginAt 값이 현재 시간으로 변경된다.")
    @Test
    void login() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        Customer customer = new Customer(customerId, name, email);
        LocalDateTime prevLastLoginAt = customer.getLastLoginAt();

        //when
        customer.login();
        LocalDateTime currentLastLoginAt = customer.getLastLoginAt();

        //then
        assertThat(currentLastLoginAt)
                .isNotEqualTo(prevLastLoginAt)
                .isAfter(prevLastLoginAt);
    }

    @DisplayName("Customer가 주어졌을 때, changeName()을 실행하면, name 값이 변경된다.")
    @ParameterizedTest
    @ValueSource(strings = "bCustomer, cCustomer")
    void changeName(String changeName) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        Customer customer = new Customer(customerId, name, email);

        //when
        customer.changeName(changeName);

        //then
        assertThat(customer.getName()).isEqualTo(changeName);
    }

    @DisplayName("변경 할 이름이 비어있거나 없으면, changeName()을 실행할 때 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwExceptionWhenChangeNameIsNullOrEmpty(String changeName) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";
        Customer customer = new Customer(customerId, name, email);

        //when, then
        Assertions.assertThatThrownBy(() -> customer.changeName(changeName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 이름이 비어있습니다.");
    }

    @DisplayName("고객의 아이디를 입력하지 않으면, 고객을 생성할 때 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    void throwExceptionWhenCustomerIdIsNull(UUID customerId) {
        //given
        String name = "aCustomer";
        String email = "mgtmh991013@naver.com";

        //when, then
        Assertions.assertThatThrownBy(() -> new Customer(customerId, name, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 아이디가 비어있습니다.");
    }

    @DisplayName("고객의 이메일을 입력하지 않으면, 고객을 생성할 때 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwExceptionWhenEmailIsNullOrEmpty(String email) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";

        //when, then
        Assertions.assertThatThrownBy(() -> new Customer(customerId, name, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 이메일이 비어있습니다.");
    }

    @DisplayName("이메일 형식이 맞지 않으면, 고객을 생성할 때 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = "mgtmh991013naver.com, mgtmh991013gmail.com")
    void throwExceptionWhenEmailIsIncorrectFormat(String email) {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "aCustomer";

        //when, then
        assertThatThrownBy(() -> new Customer(customerId, name, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("옳바른 이메일 형식을 입력해주세요. 입력값: %s", email));
    }

    @DisplayName("고객의 이름을 입력하지 않으면, 고객을 생성할 때 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwExceptionWhenNameIsNullOrEmpty(String name) {
        //given
        UUID customerId = UUID.randomUUID();
        String email = "mgtmh991013@naver.com";

        //when, then
        Assertions.assertThatThrownBy(() -> new Customer(customerId, name, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 이름이 비어있습니다.");
    }
}
