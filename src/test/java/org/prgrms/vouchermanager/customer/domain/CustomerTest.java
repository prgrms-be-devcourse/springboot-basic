package org.prgrms.vouchermanager.customer.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    void login_lastLoginAt을_현재시간으로_바꾼다() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "foo name", "example@email.com");

        //when
        LocalDateTime loginTime = LocalDateTime.now();
        customer.login();

        //then
        assertThat(customer.getLastLoginAt()).isAfterOrEqualTo(loginTime);
    }

    @Test
    void changeName_이름을_바꾼다() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "foo name", "example@email.com");
        String newName = "newName";

        //when
        customer.changeName(newName);

        //then
        assertThat(customer.getName()).isEqualTo(newName);
    }

    @Test
    void 생성자에서_Name이_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), "  ", "example@email.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changeName_이름이_공백이면_예외를_던진다() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "foo name", "example@email.com");

        //then
        assertThatThrownBy(() -> customer.changeName("  ")).isInstanceOf(IllegalArgumentException.class);
    }
}