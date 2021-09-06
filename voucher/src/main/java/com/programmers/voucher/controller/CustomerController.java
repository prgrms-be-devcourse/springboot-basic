package com.programmers.voucher.controller;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.service.customer.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService basicCustomerService;

    public CustomerController(CustomerService basicCustomerService) {
        this.basicCustomerService = basicCustomerService;
    }

    @GetMapping("/create")
    public String requestCreateCustomer() {
        return "customer/create.html";
    }

    @PostMapping("/create")
    public String submitCreateCustomer(@RequestParam(name="username", defaultValue = "") String username,
                                       @RequestParam(name="alias", defaultValue = "") String alias,
                                       Model model) {
        if(username.isBlank() || alias.isBlank()) {
            model.addAttribute("username", username);
            model.addAttribute("alias", alias);
            model.addAttribute("error", "Required field should not be empty.");
            return "customer/create.html";
        }

        Customer customer = basicCustomerService.create(username, alias);
        return "redirect:/customer/read?id=" + customer.getId();
    }

    @GetMapping("/read")
    public String getCustomer(@RequestParam(name = "id", required = false) Long id,
                              Model model) {
        model.addAttribute("id", id);

        if (id == null || id < 1) return "customer/read.html";

        basicCustomerService.findById(id).ifPresentOrElse(
                customer -> model.addAttribute("customer", customer),
                () -> model.addAttribute("error", "No customer found."));

        return "customer/read.html";
    }

    @GetMapping("/list")
    public String getCustomers(Model model) {
        model.addAttribute("customers", basicCustomerService.listAll());

    }
}
