package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerViewController {
    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public String createCustomer(CustomerCreateRequest request) {
        customerService.createCustomer(request);
        return "redirect:/customers";
    }

    @GetMapping
    public String getAllCustomers(Model model) {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);

        return "customers/index";
    }

    @GetMapping("/{customerId}")
    public String getCustomer(@PathVariable UUID customerId, Model model) {
        CustomerResponse customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);

        return "customers/detail";
    }

    @PostMapping("/{customerId}")
    public String deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }
}
