package com.zerozae.voucher.dto.customer;

import com.zerozae.voucher.domain.customer.CustomerType;
import lombok.Getter;

@Getter
public class CustomerRequest {
    private String customerName;
    private CustomerType customerType;

    public CustomerRequest(String customerName, CustomerType customerType) {
        this.customerName = customerName;
        this.customerType = customerType;
    }
}
