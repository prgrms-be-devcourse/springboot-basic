package com.prgrms.voucher_manager.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class CustomerTest {


    @Test
    @DisplayName("이름이 비어있는 경우 customer를 만들 수 없다.")
    void testBlankNameCustomer() {

        assertThrows(RuntimeException.class, () -> new SimpleCustomer(UUID.randomUUID(), "", "test@gmail.com", LocalDateTime.now()));
        assertThrows(RuntimeException.class, () -> new SimpleCustomer(UUID.randomUUID(), "  ", "test@gmail.com", LocalDateTime.now()));

    }


}