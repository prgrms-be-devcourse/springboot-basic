package com.programmers.vouchermanagement.customer.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.customer.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerAdminController {
    private final CustomerService customerService;

    public CustomerAdminController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String readAllCustomers(Model model) {
        List<CustomerResponse> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customers/customers";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/customer";
    }

    @PostMapping("/create")
    public String create(String name, Model model) {
        CustomerResponse customer = customerService.create(name);
        model.addAttribute("customer", customer);
        return "redirect:/customers/" + customer.customerId();
    }

    @GetMapping("/{customerId}")
    public String findById(@PathVariable UUID customerId, Model model) {
        CustomerResponse customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        return "customers/customer";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute UpdateCustomerRequest request, Model model) {
        CustomerResponse customer = customerService.update(request);
        model.addAttribute("customer", customer);
        return "redirect:/customers/" + customer.customerId();
    }

    @PostMapping("/{customerId}/delete")
    public String deleteById(@PathVariable UUID customerId) {
        customerService.deleteById(customerId);
        return "customers/deleted";
    }
}
