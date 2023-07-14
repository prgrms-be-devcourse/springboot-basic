package org.weekly.weekly.customer.controller;

import org.springframework.stereotype.Controller;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerResponse;
import org.weekly.weekly.customer.dto.response.CustomersResponse;
import org.weekly.weekly.customer.service.CustomerService;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResponse createCustomer(CustomerCreationRequest creationRequest) {
        return customerService.createCustomer(creationRequest);
    }

    public void deleteCustomer(CustomerUpdateRequest updateRequest) {
        customerService.deleteCustomer(updateRequest);
    }

    public void deleteAllCustomer() {
        customerService.deleteAllCustomers();
    }

    public CustomerResponse findDetailCustomer(CustomerUpdateRequest updateRequest) {
        return customerService.findDetailCustomer(updateRequest);
    }

    public CustomersResponse findAllCustomer() {
        return customerService.findAllCustomer();
    }

    public CustomerResponse updateCustomer(CustomerUpdateRequest updateRequest) {
        return customerService.updateCustomer(updateRequest);
    }
}
