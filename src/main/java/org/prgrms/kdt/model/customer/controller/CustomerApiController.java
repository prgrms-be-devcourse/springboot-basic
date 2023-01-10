package org.prgrms.kdt.model.customer.controller;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerApiController {

    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> customerListsPage() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/v1/blacklists")
    public List<Customer> blacklistsPage() {
        return customerService.getAllBlacklist();
    }
}
