package com.prgrms.management.customer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class CustomerTest {

    @Test
    void NORMAL_Customer_객체_생성() {
        //given
        Customer customerNormal = new Customer(CustomerType.NORMAL);
        //when
        //then
        Assertions.assertThat(customerNormal.getCustomerType()).isEqualTo(CustomerType.NORMAL);
    }

    @Test
    void BLACKLIST_Customer_객체_생성() {
        //given
        Customer customerBlack = new Customer(CustomerType.BLACKLIST);
        //when
        //then
        Assertions.assertThat(customerBlack.getCustomerType()).isEqualTo(CustomerType.BLACKLIST);
    }

    @Test
    void 잘못된_Customer_객체_생성() {
        //given
        String input = "gold";
        //then
        Assertions.assertThatThrownBy(() -> new Customer(CustomerType.of(input)))
                .isInstanceOf(NoSuchElementException.class);
    }

}