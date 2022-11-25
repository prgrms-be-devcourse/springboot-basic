package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.CustomerRequest;
import com.programmers.voucher.io.Message;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerController {
    private final CustomerService customerService;
    private final Output output;

    public CustomerController(CustomerService customerService, Output output) {
        this.customerService = customerService;
        this.output = output;
    }

    public void createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerService.create(customerRequest);
        output.printFormat(Message.WELCOME_CUSTOMER, customer.getCustomerName());
    }

    public void findCustomerByVoucher(UUID inputVoucherId) {
        Customer customer = customerService.findByVoucher(inputVoucherId);
        output.printFormat(Message.OWN_VOUCHER, customer.getCustomerName());
    }
}
