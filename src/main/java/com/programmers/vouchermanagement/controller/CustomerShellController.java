package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import com.programmers.vouchermanagement.infra.io.ConsoleOutput;
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
        GetCustomersRequestDto request = GetCustomersRequestDto.builder().blacklisted(true).build();

        List<Customer> customers = customerService.getCustomers(request);

        for (Customer customer : customers) {
            ConsoleOutput.println(customer.toString());
        }
    }
}
