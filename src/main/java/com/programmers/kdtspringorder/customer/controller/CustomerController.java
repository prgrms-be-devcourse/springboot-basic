package com.programmers.kdtspringorder.customer.controller;

import com.programmers.kdtspringorder.customer.model.Customer;
import com.programmers.kdtspringorder.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String findCustomer(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers", customerList);
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage() {
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest) {
        try {
            customerService.createCustomer(createCustomerRequest.email(), createCustomerRequest.name());
        } catch (RuntimeException e) {
            return "views/new-customers";
        }
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (optionalCustomer.isPresent()) {
            model.addAttribute("customer", optionalCustomer.get());
            return "views/customer-details";
        }
        return "views/404";
    }
}
