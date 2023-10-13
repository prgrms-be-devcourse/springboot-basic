package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.domain.customer.Customer;
import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.util.LoggingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @ShellMethod(key = "blacklist")
    public void blacklist() {
        log.info(LoggingUtil.createRunLogMessage("blacklist"));

        List<Customer> customers = customerService.blacklist();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }
}