package org.prgrms.voucherapplication.customer.controller;

import org.prgrms.voucherapplication.customer.controller.dto.CreateCustomerRequest;
import org.prgrms.voucherapplication.customer.entity.Customer;
import org.prgrms.voucherapplication.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<Customer> allCustomers = customerService.getAllCustomers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("customers", allCustomers);
        return "/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<Customer> maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "customer-details";
        } else {
            return "404";
        }
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage() {
        return "new-customers";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.getEmail(), createCustomerRequest.getName());
        return "redirect:/customers";
    }
}
