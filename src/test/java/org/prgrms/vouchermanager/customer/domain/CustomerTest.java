package org.prgrms.vouchermanager.customer.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

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