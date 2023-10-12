package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @ShellMethod(key = "blacklist")
    public void blacklist() {
        List<Customer> customers = customerService.blacklist();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }
}