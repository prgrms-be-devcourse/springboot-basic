package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerDto> findBlackCustomers() {
        return customerService.getBlackCustomers().stream()
                .map(CustomerDto::of)
                .toList();
    }

    public CustomerDto registerCustomer(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.registCustomer(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.updateCustomer(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public List<CustomerDto> findAllCustomers() {
        return customerService.findAllCustomers()
                .stream()
                .map(CustomerDto::of)
                .toList();
    }

    public CustomerDto findCustomerById(UUID customerId) {
        return CustomerDto.of(customerService.findCustomerById(customerId));
    }

    public CustomerDto findCustomerByName(String name) {
        return CustomerDto.of(customerService.findCustomerByName(name));
    }

    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
    }

    public CustomerDto deleteCustomerById(UUID customerId) {
        return CustomerDto.of(customerService.deleteCustomerById(customerId));
    }

}
