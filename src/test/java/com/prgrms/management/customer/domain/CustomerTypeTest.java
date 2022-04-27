package com.prgrms.management.customer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTypeTest {

    @Test
    void 성공_Customer_Type_주입() {
        //given
        String input = "normal";
        //when
        CustomerType customerType = CustomerType.of(input);
        //then
        Assertions.assertThat(customerType).isEqualTo(CustomerType.NORMAL);
    }

    @Test
    void 잘못된_Customer_Type_주입() {
        //given
        String input = "gold";
        //then
        Assertions.assertThatThrownBy(() -> CustomerType.of(input))
                .isInstanceOf(NoSuchElementException.class);
    }

}