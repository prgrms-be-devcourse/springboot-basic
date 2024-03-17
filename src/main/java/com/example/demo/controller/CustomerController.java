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
        CustomerValidator.validateAge(String.valueOf(age));
        return customerService.save(name, age);
    }

    public List<CustomerDto> readList() {
        return customerService.readList();
    }

    public CustomerDto findById(UUID id) {
        return customerService.findById(id);
    }

    public void updateName(UUID id, String name) {
        CustomerValidator.validateName(name);
        customerService.updateName(id, name);
    }

    public void deleteById(UUID id) {
        customerService.deleteById(id);
    }
}
