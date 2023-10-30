package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController implements ApplicationController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> findAll() {
        return customerService.findAll();
    }

    public List<Customer> findAllBlackList() {
        return customerService.findBlackAll();
    }

    public Customer create(Customer customer) {
        return customerService.create(customer);
    }

    public Customer update(Customer updateCustomer) {
        return customerService.update(updateCustomer);
    }

    public void delete(UUID deleteCustomerId) {
        customerService.delete(deleteCustomerId);
    }
}
