package com.programmers.springbasic.domain.customer.validator;

import com.programmers.springbasic.domain.customer.dto.request.CustomerUpdateRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerUpdateRequestValidatorTest {
    @Test
    void 고객_정보_갱신_요청_테스트() {
        // DTO Setting
        CustomerUpdateRequestDTO validRequestDTO = new CustomerUpdateRequestDTO("7d255006-2730-4f55-a444-91cb2e21a803", "test", "test@gmail.com");  // 유효한 경우
        CustomerUpdateRequestDTO inValidCustomerIdRequestDTO = new CustomerUpdateRequestDTO("1234-1425-12313", "test", "test@gmail.com");  // 유효하지 않은 고객 ID 형식인 경우
        CustomerUpdateRequestDTO blankNameRequestDTO = new CustomerUpdateRequestDTO("7d255006-2730-4f55-a444-91cb2e21a803", "", "test@gmail.com");  // 이름이 공백인 경우
        CustomerUpdateRequestDTO blankEmailRequestDTO = new CustomerUpdateRequestDTO("7d255006-2730-4f55-a444-91cb2e21a803", "test", "");   // 이메일이 공백인 경우
        CustomerUpdateRequestDTO wrongEmailFormatRequestDTO = new CustomerUpdateRequestDTO("7d255006-2730-4f55-a444-91cb2e21a803", "test", "wrong@gmail");  // 이메일 형식이 올바르지 않은 경우

        // 정상적인 인자가 전달되었을 경우
        assertDoesNotThrow(() -> CustomerUpdateRequestValidator.validateUpdateCustomerRequest(validRequestDTO));

        /**
         * 예외 상황인 경우
         */
        // 유효하지 않은 고객 ID 형식인 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerUpdateRequestValidator.validateUpdateCustomerRequest(inValidCustomerIdRequestDTO));

        // 이름이 공백인 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerUpdateRequestValidator.validateUpdateCustomerRequest(blankNameRequestDTO));

        // 이메일이 공백인 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerUpdateRequestValidator.validateUpdateCustomerRequest(blankEmailRequestDTO));

        // 이메일 형식이 올바르지 않은 경우
        assertThrows(IllegalArgumentException.class, () -> CustomerUpdateRequestValidator.validateUpdateCustomerRequest(wrongEmailFormatRequestDTO));
    }
}