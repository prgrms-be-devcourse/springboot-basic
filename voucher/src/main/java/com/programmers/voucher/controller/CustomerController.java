package com.programmers.voucher.controller;

import com.programmers.voucher.service.customer.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService basicCustomerService;

    public CustomerController(CustomerService basicCustomerService) {
        this.basicCustomerService = basicCustomerService;
    }

    @GetMapping("/read")
    public String getCustomer(@RequestParam(name = "id", required = false) Long id,
                              Model model) {
        model.addAttribute("id", id);

        if (id == null || id < 1) return "customer/read.html";

        basicCustomerService.findById(id).ifPresentOrElse(
                customer -> {
                    model.addAttribute("customer", customer);
                },
                () -> model.addAttribute("error", "No customer found."));

        return "customer/read.html";
    }

}
