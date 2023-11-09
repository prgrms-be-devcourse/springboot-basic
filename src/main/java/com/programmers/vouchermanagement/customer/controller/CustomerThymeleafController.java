package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Profile("thyme")
@RequestMapping("/customers")
@Controller
public class CustomerThymeleafController {
    private final CustomerService customerService;

    public CustomerThymeleafController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/new")
    public String create(CreateCustomerRequest createCustomerRequest) {
        customerService.create(createCustomerRequest);
        return "redirect:/customers";
    }

    @GetMapping("/new")
    public String viewCreatePage() {
        return "customer/customer-new";
    }

    @GetMapping
    public String readAll(@RequestParam(required = false, defaultValue = "all") String type, Model model) {
        if (type.equals("blacklist")) {
            model.addAttribute("mode", "blacklist");
            model.addAttribute("customers", customerService.readAllBlackCustomer());
            return "customer/customers";
        }
        model.addAttribute("mode", "all");
        model.addAttribute("customers", customerService.readAll());
        return "customer/customers";
    }
}
