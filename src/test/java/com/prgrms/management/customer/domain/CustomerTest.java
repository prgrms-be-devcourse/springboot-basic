package com.prgrms.management.customer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class CustomerTest {
    @Test
    void 성공_Customer_객체_생성() {
        //given
        String blacklist = "BLACKLIST";
        CustomerRequest customerB = new CustomerRequest("customerB", "customerB@naver.com", blacklist);
        //when
        Customer customerBlack = new Customer(customerB);
        //then
        Assertions.assertThat(customerBlack.getCustomerType()).isEqualTo(CustomerType.BLACKLIST);
    }

    @Test
    void 잘못된_Customer_객체_생성() {
        //given
        String input = "GOLD";
        //then
        Assertions.assertThatThrownBy(() -> new Customer(new CustomerRequest("customerB", "customerB@naver.com", input)))
                .isInstanceOf(NoSuchElementException.class);
    }

}