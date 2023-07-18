package com.programmers.springweekly.dto.customer.request;

import com.programmers.springweekly.domain.customer.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "이름은 영어로만 입력되어야 합니다.")
    private String customerName;

    @Email(message = "이메일 양식에 맞춰서 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String customerEmail;

    @NotNull(message = "타입은 필수 입력 값입니다.")
    private CustomerType customerType;

    @Builder
    public CustomerCreateRequest(String customerName, String customerEmail, CustomerType customerType) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }

}
