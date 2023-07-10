package com.programmers.springbasic.domain.customer.validator;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIdValidatorTest {
    @Test
    void 고객_ID_검증기_테스트() {
        String validCustomerId = UUID.randomUUID().toString();
        String inValidCustomerId = "1234-5235-26345";

        // 유효한 customerId
        assertDoesNotThrow(() -> CustomerIdValidator.validateCustomerId(validCustomerId));

        // 유효하지 않은 customerId
        assertThrows(IllegalArgumentException.class, () -> CustomerIdValidator.validateCustomerId(inValidCustomerId));
    }
}