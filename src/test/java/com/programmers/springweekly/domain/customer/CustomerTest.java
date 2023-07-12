package com.programmers.springweekly.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CustomerTest {

    @Test
    @DisplayName("고객을 생성할 수 있다.")
    void createCustomer() {
        // given && when
        Customer customer = new Customer(UUID.randomUUID(), "changhyeonh", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);

        // then
        assertThat(customer).isInstanceOf(Customer.class);
    }
    
}
