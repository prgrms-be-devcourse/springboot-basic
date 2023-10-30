package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.dto.customer.request.GetCustomersRequestDto;
import com.programmers.vouchermanagement.dto.customer.response.CustomerResponseDto;
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

        List<CustomerResponseDto> customers = customerService.getCustomers(request);

        for (CustomerResponseDto customer : customers) {
            ConsoleOutput.println(customer.toString());
        }
    }
}
