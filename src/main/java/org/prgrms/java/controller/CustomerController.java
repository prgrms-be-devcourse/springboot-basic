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
        return customerService.saveCustomer(name, email);
    }

    public Customer findCustomer(String column, String value) {
        switch (column.toLowerCase()) {
            case "id": return customerService.getCustomerById(UUID.fromString(value));
            case "name": return customerService.getCustomerByName(value);
            case "email": return customerService.getCustomerByEmail(value);
        }
        throw new IllegalArgumentException("Wrong column");
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

    public long deleteCustomers() {
        return customerService.deleteAllCustomers();
    }
}
