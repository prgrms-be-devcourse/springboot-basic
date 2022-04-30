package org.devcourse.voucher.customer.controller;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.devcourse.voucher.customer.service.CustomerService;

import java.util.UUID;

public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer createCustomerMapper(String name, String email) {
        return customerService.createCustomer(UUID.randomUUID(), name, new Email(email));
    }
}
