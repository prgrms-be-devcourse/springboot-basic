package org.prgrms.java.controller;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer createCustomer(String name, String email) {
        return customerService.createCustomer(name, email);
    }

    public Customer findCustomer(String column, String value) {
        return customerService.getCustomer(column, value);
    }

    public List<Customer> findCustomers(boolean blacklistOnly) {
        return blacklistOnly ? customerService.getAllBlackCustomers() : customerService.getAllCustomers();
    }

    public Customer updateCustomer(UUID customerId, String name, String email, boolean isBlocked) {
        return customerService.updateCustomer(customerId, name, email, isBlocked);
    }

    public void deleteCustomer(UUID customerId) {
        customerService.deleteCustomer(customerId);
    }

    public void deleteCustomers() {
        customerService.deleteAllCustomers();
    }
}
