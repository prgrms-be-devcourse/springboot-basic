package com.zerozae.voucher.dto.customer;

import com.zerozae.voucher.domain.customer.CustomerType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CustomerUpdateRequest {

    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "회원 이름은 알파벳과 한글만 포함해야 합니다.")
    @Length(min = 2, max = 10, message = "회원 이름은 2자에서 10자 사이로 입력해주세요.")
    private String customerName;

    @NotNull(message = "회원 타입은 필수 입력란입니다.")
    private String customerType;

    public CustomerUpdateRequest(String customerName, String customerType) {
        this.customerName = customerName;
        this.customerType = customerType;
    }
}
