package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import com.example.demo.view.validate.CustomerValidator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerDto create(String name, int age) {
        CustomerValidator.validateName(name);
        CustomerValidator.validateAge(age);
        return customerService.save(name, age);
    }

    public CustomerDto read(UUID id) {
        return customerService.read(id);
    }

    public List<CustomerDto> readList() {
        return customerService.readAll();
    }

    public void updateName(UUID id, String name) {
        CustomerValidator.validateName(name);
        customerService.updateName(id, name);
    }

    public void deleteById(UUID id) {
        customerService.deleteById(id);
    }
}
