package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.engine.service.CustomerService;
import com.programmers.assignment.voucher.util.dto.CustomerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;


@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String customerList(Model model) {
        List<Customer> customers = customerService.findCustomers();
        model.addAttribute("customers", customers);
        return "customer";
    }

    @GetMapping("/customers/{customerUuid}")
    public String customerDetails(Model model, @PathVariable UUID customerUuid) {
        var customer = customerService.findCustomerByUuid(customerUuid);
        model.addAttribute("customer", customer);
        return "customer-details";
    }

    @GetMapping("/customers/new")
    public String createCustomer() {
        return "customer-form";
    }

    @PostMapping("/customers/new")
    public String createCustomer(CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return "redirect:/customers";
    }
}
