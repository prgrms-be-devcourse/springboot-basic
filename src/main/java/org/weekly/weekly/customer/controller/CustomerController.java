package org.weekly.weekly.customer.controller;

import org.springframework.stereotype.Controller;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.service.CustomerService;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createCustomer(CustomerCreationRequest creationRequest) {
        this.customerService.createCustomer(creationRequest);
    }

    public void deleteCustomer(CustomerUpdateRequest updateRequest) {
        this.customerService.deleteCustomer(updateRequest);
    }

    public void deleteAllCustomer() {
        this.customerService.deleteAllCustomer();
    }

    public void searchDetailCustomer(CustomerUpdateRequest updateRequest) {
        this.customerService.searchDetailCustomer(updateRequest);
    }

    public void searchAllCustomer() {
        this.customerService.searchAllCustomer();
    }
}
