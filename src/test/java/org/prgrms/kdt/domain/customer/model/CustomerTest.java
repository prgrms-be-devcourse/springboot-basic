package org.prgrms.kdt.domain.customer.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    @DisplayName("고객이 정상적으로 생성된다.")
    void createCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "park";
        String email = "db2@naver.com";
        LocalDateTime now = LocalDateTime.now();
        //when
        Customer customer = new Customer(customerId, name, email, now, now);
        //then
        assertThat(customer.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("고객의 이메일 주소가 유효하지 않을경우 예외가 발생한다.")
    void createCustomer_exception() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "park";
        String email = "d123@";
        LocalDateTime now = LocalDateTime.now();
        //when
        //then
        assertThatThrownBy(() -> new Customer(customerId, name, email, now, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 주소를 확인해주세요");
    }
}