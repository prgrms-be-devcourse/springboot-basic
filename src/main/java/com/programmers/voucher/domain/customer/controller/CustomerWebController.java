package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerWebController {
    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String findCustomers(Model model) {
        List<CustomerDto> customers = customerService.findCustomers();
        model.addAttribute("customers", customers);
        return "customers/customer-list";
    }

    @GetMapping("/customers/new")
    public String createCustomer() {
        return "customers/customer-create";
    }

    @PostMapping("/customers/new")
    public String createCustomer(@ModelAttribute @Valid CustomerCreateRequest request) {
        UUID customerId = customerService.createCustomer(request.getEmail(), request.getName());
        return "redirect:/customers/" + customerId;
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(Model model,
                               @PathVariable UUID customerId) {
        CustomerDto customer = customerService.findCustomer(customerId);
        model.addAttribute("customer", customer);
        return "customers/customer";
    }

    @PostMapping("/customers/{customerId}")
    public String updateCustomer(@PathVariable UUID customerId,
                                 @ModelAttribute @Valid CustomerUpdateRequest request) {
        customerService.updateCustomer(customerId, request.getName(), request.isBanned());
        return "redirect:/customers/{customerId}";
    }

    @PostMapping("/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }
}
