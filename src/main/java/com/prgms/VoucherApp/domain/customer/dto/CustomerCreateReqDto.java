package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.CustomerStatus;

public class CustomerCreateReqDto {

    private CustomerStatus customerStatus;

    public CustomerCreateReqDto(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }
}
