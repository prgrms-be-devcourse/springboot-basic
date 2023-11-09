package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    public List<Customer> findBlacklist() {
        return service.findBlacklist();
    }

    public Customer create(String name, int year) {
        return service.create(name, year);
    }

    ;

    public List<Customer> list() {
        return service.findAll();
    }

    public Customer updateYearOfBirth(UUID id, int year) {
        return service.updateYearOfBirth(id, year);
    }

    public Customer updateName(UUID id, String name) {
        return service.updateName(id, name);
    }

    public int delete(UUID id) {
        return service.delete(id);
    }
}
