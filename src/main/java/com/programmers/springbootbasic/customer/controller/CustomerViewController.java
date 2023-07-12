package com.programmers.springbootbasic.customer.controller;

import com.programmers.springbootbasic.customer.dto.CustomerCreateRequestDto;
import com.programmers.springbootbasic.customer.dto.CustomerResponseDto;
import com.programmers.springbootbasic.customer.dto.CustomersResponseDto;
import com.programmers.springbootbasic.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("customer")
@Controller
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        CustomersResponseDto customers = customerService.findAll();
        model.addAttribute("customers", customers);

        return "customer/customer-list";
    }

    @GetMapping("/create")
    public String create() {
        return "customer/customer-form";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute CustomerCreateRequestDto customerCreateRequestDto) {
        customerService.save(customerCreateRequestDto);

        return "redirect:/customer/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") UUID id, Model model) {
        CustomerResponseDto customerResponseDto = customerService.findById(id);
        model.addAttribute("customer", customerResponseDto);

        return "customer/customer-detail";
    }
}