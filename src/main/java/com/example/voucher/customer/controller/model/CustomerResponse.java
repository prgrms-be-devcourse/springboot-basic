package com.example.voucher.customer.controller.model;

import java.util.Arrays;
import java.util.List;
import com.example.voucher.customer.service.dto.CustomerDTO;

public class CustomerResponse {

    private final List<CustomerDTO> customers;

    public CustomerResponse(List<CustomerDTO> customers) {
        this.customers = customers;
    }

    public CustomerResponse(CustomerDTO customers) {
        this.customers = Arrays.asList(customers);
    }

    public List<CustomerDTO> getCustomers() {
        return customers;
    }
}
