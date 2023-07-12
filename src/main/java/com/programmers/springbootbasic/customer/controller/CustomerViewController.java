package com.programmers.springbootbasic.customer.controller;

import com.programmers.springbootbasic.customer.dto.CustomerCreateRequestDto;
import com.programmers.springbootbasic.customer.dto.CustomersResponseDto;
import com.programmers.springbootbasic.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}