package com.programmers.springbasic.domain.customer.validator;

import com.programmers.springbasic.domain.customer.dto.request.CustomerCreateRequestDTO;
import lombok.Getter;

@Getter
public class CustomerCreateRequestValidator {
    private static final String VALID_EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String INVALID_NAME_MESSAGE = "공백은 이름으로 사용할 수 없습니다.";
    private static final String INVALID_EMAIL_MESSAGE = "잘못된 이메일 형식입니다.";

    public static void validateCreateCustomerRequest(CustomerCreateRequestDTO customerCreateRequestDTO) {
        String name = customerCreateRequestDTO.getName();
        String email = customerCreateRequestDTO.getEmail();

        if (name.isBlank()) {
            throw new IllegalArgumentException(INVALID_NAME_MESSAGE);
        }

        if (!email.matches(VALID_EMAIL_REGEXP)) {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }
    }
}
