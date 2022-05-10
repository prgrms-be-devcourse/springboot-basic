package org.prgrms.voucher.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.prgrms.voucher.models.Customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomerRequestDto {

    @NotNull
    @NotBlank
    private final String customerName;

    @JsonCreator
    public CustomerRequestDto(String customerName) {

        this.customerName = customerName;
    }

    public Customer toDomain() {

        return new Customer(this.customerName);
    }

    public String getCustomerName() {

        return customerName;
    }
}