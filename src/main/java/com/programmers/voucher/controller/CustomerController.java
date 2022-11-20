package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.io.View;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerController {
    private final CustomerService customerService;
    private final View view;

    public CustomerController(CustomerService customerService, View view) {
        this.customerService = customerService;
        this.view = view;
    }

    public void createCustomer(CustomerDto customerDto) {
        Customer customer = customerService.create(customerDto);
        view.printCustomer(customer);
    }

    public void findCustomerByVoucher(UUID inputVoucherId) {
        Customer customer = customerService.findByVoucher(inputVoucherId);
        view.printCustomerWithVoucher(customer);
    }
}
