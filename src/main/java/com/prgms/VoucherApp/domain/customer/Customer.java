package com.prgms.VoucherApp.domain.customer;

import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;

import java.util.UUID;

public class Customer {

    private UUID customerId;
    private CustomerStatus customerStatus;

    private Customer(UUID customerId, CustomerStatus customerStatus) {
        this.customerId = customerId;
        this.customerStatus = customerStatus;
    }

    public CustomerDto convertCustomerDto() {
        return new CustomerDto(customerId.toString(), customerStatus);
    }

    public static Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getCustomerId(), customerDto.getCustomerStatus());
        return customer;
    }
}
