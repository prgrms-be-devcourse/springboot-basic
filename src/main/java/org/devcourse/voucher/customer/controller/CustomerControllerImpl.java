package org.devcourse.voucher.customer.controller;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.devcourse.voucher.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer postCreateCustomer(String name, String email) {
        return customerService.createCustomer(name, new Email(email));
    }
}
