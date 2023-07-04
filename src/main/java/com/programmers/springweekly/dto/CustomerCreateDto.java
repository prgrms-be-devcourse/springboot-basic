package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.util.Validator;
import lombok.Getter;

@Getter
public class CustomerCreateDto {

    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;

    public CustomerCreateDto(String customerName, String customerEmail, CustomerType customerType) {
        Validator.nameValidate(customerName);
        Validator.emailValidate(customerEmail);

        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }
}
