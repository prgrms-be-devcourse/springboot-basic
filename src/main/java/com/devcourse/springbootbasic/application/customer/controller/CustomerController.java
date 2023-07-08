package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> findBlackCustomers() {
        return customerService.getBlackCustomers();
    }

    public CustomerDto registCustomer(CustomerDto customerDto) {
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

    public CustomerDto findCustomerById(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.findCustomerById(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public CustomerDto findCustomerByName(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.findCustomerByName(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public CustomerDto findCustomerByEmail(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.findCustomerByEmail(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
    }

    public CustomerDto deleteCustomerById(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.deleteCustomerById(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public CustomerDto deleteCustomerByName(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.deleteCustomerByName(
                        CustomerDto.to(customerDto)
                )
        );
    }

    public CustomerDto deleteCustomerByEmail(CustomerDto customerDto) {
        return CustomerDto.of(
                customerService.deleteCustomerByEmail(
                        CustomerDto.to(customerDto)
                )
        );
    }

}
