package org.prgrms.java.controller;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer createCustomer(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerService.saveCustomer(customer);
    }

    public Customer findCustomerById(UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    public Customer findCustomerByName(String name) {
        return customerService.getCustomerByName(name);
    }

    public Customer findCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email);
    }

    public List<Customer> findCustomers() {
        return customerService.getAllCustomers();
    }

    public List<Customer> findBlacklist() {
        return customerService.getAllBlackCustomers();
    }

    public Customer updateCustomer(UUID customerId, String name, String email, boolean isBlocked) {
        Customer customer = customerService.getCustomerById(customerId);
        customer.setName(name);
        customer.setEmail(email);
        customer.setBlocked(isBlocked);
        return customerService.updateCustomer(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerService.deleteCustomer(customerId);
    }

    public long deleteCustomers() {
        return customerService.deleteAllCustomers();
    }
}
