package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerConsoleController {
    private final CustomerService customerService;

    public CustomerConsoleController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerDto> findBlacklistCustomers() {
        return customerService.findBlacklistCustomers();
    }

    public List<CustomerDto> findCustomers() {
        return customerService.findCustomers();
    }

    public UUID createCustomer(CustomerCreateRequest request) {
        return customerService.createCustomer(request.getEmail(), request.getName());
    }

    public void updateCustomer(CustomerUpdateRequest request) {
        customerService.updateCustomer(request.getCustomerId(), request.getName(), request.isBanned());
    }

    public void deleteCustomer(UUID customerId) {
        customerService.deleteCustomer(customerId);
    }
}
