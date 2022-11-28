package org.prgrms.kdt.controller.customer.api;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.service.customer.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
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
