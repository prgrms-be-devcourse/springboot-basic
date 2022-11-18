package org.prgrms.kdt.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exceptions.CustomerException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("이름은 빈 값일 수 없다.")
    void test() {
        // given
        String empty_name = "";

        // when & then
        assertThrows(CustomerException.class, () -> new Customer(UUID.randomUUID().toString(), empty_name, "test@gmail.com"));
    }

}