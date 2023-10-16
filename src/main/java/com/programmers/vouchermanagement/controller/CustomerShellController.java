package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CustomerShellController {
    private final CustomerService customerService;

    @ShellMethod(key = "blacklist")
    public void blacklist() {
        List<Customer> customers = customerService.blacklist();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }
}