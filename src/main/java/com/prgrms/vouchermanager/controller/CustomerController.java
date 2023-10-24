package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.service.CustomerService;
import com.prgrms.vouchermanager.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    public List<Customer> blacklist() {
        return service.blacklist();
    }

    public Customer create(String name, int year) { return service.create(name, year); };

    public List<Customer> list() {
        return service.list();
    }

    public Customer updateYearOfBirth(UUID id, int year) {
        return service.updateYearOfBirth(id, year);
    }

    public Customer updateName(UUID id, String name) {
        return service.updateName(id, name);
    }

    public UUID delete(UUID id) {
        return service.delete(id);
    }
}
