package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.service.CustomerService;
import com.programmers.vouchermanagement.util.LoggingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class CustomerShellController {
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