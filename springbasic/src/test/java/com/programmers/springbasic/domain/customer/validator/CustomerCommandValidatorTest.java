package com.programmers.springbasic.domain.customer.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerCommandValidatorTest {

    @Test
    void getInputCommand() {
        /*
        exit|create|list|read|update|delete|show
         */
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("exit"));
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("create"));
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("list"));
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("read"));
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("update"));
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("delete"));
        assertDoesNotThrow(() -> CustomerCommandValidator.validateCustomerCommand("show"));

        assertThrows(IllegalArgumentException.class, () -> CustomerCommandValidator.validateCustomerCommand("invalid command"));
    }
}