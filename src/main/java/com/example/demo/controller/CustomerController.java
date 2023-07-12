package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDto create(String name, int age) {
        return customerService.save(name, age);
    }

    public CustomerDto read(UUID id) {
        return customerService.read(id);
    }

    public List<CustomerDto> readList() {
        return customerService.readAll();
    }

    public void updateName(UUID id, String name) {
        customerService.updateName(id, name);
    }

    public void deleteById(UUID id) {
        customerService.deleteById(id);
    }
}
