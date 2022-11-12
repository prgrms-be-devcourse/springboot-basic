package com.programmers.voucher.controller;

import com.programmers.voucher.io.View;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {
    private final View view;
    private final CustomerService customerService;

    public CustomerController(View view, CustomerService customerService) {
        this.view = view;
        this.customerService = customerService;
    }

    public void getBlacklist() {
        List<Customer> blacks = customerService.findAllBlack();
        view.printBlacks(blacks);
    }
}
