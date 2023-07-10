package com.programmers.springbasic.domain.customer.validator;

import com.programmers.springbasic.domain.customer.dto.request.CustomerCreateRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CustomerCreateRequestValidatorTest {
    @Test
    void 고객_생성_커멘드_유효성_검증() {
        // DTO Setting
        CustomerCreateRequestDTO validRequestDTO = new CustomerCreateRequestDTO("test", "test@gmail.com");  // 유효한 경우
        CustomerCreateRequestDTO blankNameRequestDTO = new CustomerCreateRequestDTO("", "test@gmail.com");  // 이름이 공백인 경우
        CustomerCreateRequestDTO blankEmailRequestDTO = new CustomerCreateRequestDTO("test", "");   // 이메일이 공백인 경우
        CustomerCreateRequestDTO wrongEmailFormatRequestDTO = new CustomerCreateRequestDTO("test", "wrong@gmail");  // 이메일 형식이 올바르지 않은 경우

        // 정상적인 인자가 전달되었을 경우
        assertDoesNotThrow(() -> CustomerCreateRequestValidator.validateCreateCustomerRequest(validRequestDTO));

        /**
         * 예외 상황인 경우
         */
        // 이름이 공백인 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerCreateRequestValidator.validateCreateCustomerRequest(blankNameRequestDTO));

        // 이메일이 공백인 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerCreateRequestValidator.validateCreateCustomerRequest(blankEmailRequestDTO));

        // 이메일 형식이 올바르지 않은 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerCreateRequestValidator.validateCreateCustomerRequest(wrongEmailFormatRequestDTO));
    }
}