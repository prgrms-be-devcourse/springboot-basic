package com.programmers.customer.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CustomerTest {

    @DisplayName("customer 객체를 생성한다")
    @Test
    void customer() {
        //given
        //when
        Customer customer = new Customer(UUID.randomUUID(), "test");

        //then
        assertThat(customer, instanceOf(Customer.class));
    }

    @DisplayName("UUID를 입력하지 않고 Customer 객체를 생성한다")
    @Test
    void customerWithoutUuidInput() {
        //given
        //when
        Customer customer = new Customer("test");

        //then
        assertThat(customer, instanceOf(Customer.class));
    }

    @DisplayName("이름이 비었을 경우 예외처리한다")
    @Test
    void customerWithEmptyName() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new Customer(UUID.randomUUID(), ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름 길이 제한을 넘었을 경우 예외처리한다")
    @Test
    void customerWithLongName() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new Customer(UUID.randomUUID(), "abbababbbbabababbabbaaababababab"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}