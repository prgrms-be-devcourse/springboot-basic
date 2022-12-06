package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.CustomerCreateRequest;
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

    public void createCustomer(CustomerCreateRequest customerCreateRequest) {
        Customer customer = customerService.create(customerCreateRequest);
        output.printFormat(Message.WELCOME_CUSTOMER, customer.getCustomerName());
    }

    public void findCustomerByVoucher(UUID inputVoucherId) {
        Customer customer = customerService.findByVoucher(inputVoucherId);
        output.printFormat(Message.OWN_VOUCHER, customer.getCustomerName());
    }
}
