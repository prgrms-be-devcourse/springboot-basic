package com.programmers.springweekly.dto.customer.request;

import com.programmers.springweekly.domain.customer.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank
    private String customerName;

    @Email
    @NotBlank
    private String customerEmail;

    @NotNull
    private CustomerType customerType;

    @Builder
    public CustomerCreateRequest(String customerName, String customerEmail, CustomerType customerType) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }

}
