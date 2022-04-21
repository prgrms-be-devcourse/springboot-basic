package org.prgrms.vouchermanager.customer.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    void 생성자에서_Name이_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), "  ", "example@email.com", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}