package com.zerozae.voucher.dto.customer;

import com.zerozae.voucher.domain.customer.CustomerType;
import lombok.Getter;

@Getter
public class CustomerUpdateRequest {

    private String customerName;
    private CustomerType customerType;

    public CustomerUpdateRequest(String customerName, CustomerType customerType) {
        this.customerName = customerName;
        this.customerType = customerType;
    }
}
