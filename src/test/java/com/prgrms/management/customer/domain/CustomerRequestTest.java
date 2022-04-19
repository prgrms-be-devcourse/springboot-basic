package com.prgrms.management.customer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRequestTest {


    @Test
    void NORMAL_CustomerRequest_객체_생성() {
        //given
        CustomerRequest customerRequest = new CustomerRequest("dhkstn", "dhkstnaos@gmail.com",
                "normal");
        //when
        //then
        Assertions.assertThat(customerRequest.getCustomerType()).isEqualTo(CustomerType.NORMAL);
    }

    @Test
    void BLACKLIST_CustomerRequest_객체_생성() {
        //given
        CustomerRequest customerRequest = new CustomerRequest("dhkstn", "dhkstnaos@gmail.com",
                "blacklist");
        //when
        //then
        Assertions.assertThat(customerRequest.getCustomerType()).isEqualTo(CustomerType.BLACKLIST);
    }
}