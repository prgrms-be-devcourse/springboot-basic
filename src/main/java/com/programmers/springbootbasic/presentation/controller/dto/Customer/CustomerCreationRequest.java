package com.programmers.springbootbasic.presentation.controller.dto.Customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerCreationRequest(
        @NotBlank
        @Size(min = 1, max = 20, message = "이름은 1자 이상 20자 이하여야 합니다.")
        String name,
        @NotNull
        @Email(message = "이메일 형식이 아닙니다.")
        String email
) {
}
