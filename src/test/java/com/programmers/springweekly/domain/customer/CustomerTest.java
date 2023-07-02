package com.programmers.springweekly.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class CustomerTest {

    @Test
    @DisplayName("고객을 생성한다.")
    void createCustomer() {
        // given && when
        Customer customer = new Customer(UUID.randomUUID(), CustomerType.BLACKLIST);
        
        // then
        assertThat(customer).isInstanceOf(Customer.class);
    }
}
