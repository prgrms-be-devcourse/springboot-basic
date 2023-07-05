package com.example.demo.customer.controller;

import com.example.demo.customer.domain.repostiory.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public String findAll(Model model) {
        model.addAttribute("customers", customerRepository.findAll());

        return "customers";
    }
}
