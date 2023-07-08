package org.weekly.weekly.customer.controller;

import org.springframework.stereotype.Controller;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerDto;
import org.weekly.weekly.customer.service.CustomerService;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDto createCustomer(CustomerCreationRequest creationRequest) {
        return customerService.createCustomer(creationRequest);
    }

    public void deleteCustomer(CustomerUpdateRequest updateRequest) {
        customerService.deleteCustomer(updateRequest);
    }

    public void deleteAllCustomer() {
        customerService.deleteAllCustomers();
    }

    public CustomerDto searchDetailCustomer(CustomerUpdateRequest updateRequest) {
        return customerService.searchDetailCustomer(updateRequest);
    }

    public List<CustomerDto> searchAllCustomer() {
        return customerService.searchAllCustomer();
    }

    public void updateCustomer(CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(updateRequest);
    }
}
