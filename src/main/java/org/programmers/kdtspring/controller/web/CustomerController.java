package org.programmers.kdtspring.controller.web;

import org.programmers.kdtspring.dto.CreateCustomerRequest;
import org.programmers.kdtspring.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showCustomersPage(Model model) {
        log.info("[CustomerController] showCustomerPage() called");

        var allCustomer = customerService.getAllCustomers();
        model.addAttribute("customers", allCustomer);
        return "customers";
    }

    @GetMapping("/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        log.info("[CustomerController] findCustomer() called");

        var customer = customerService.getCustomerById(customerId);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customerDetail";
        } else {
            return "404";
        }
    }

    @GetMapping("/new")
    public String createCustomerForm(Model model) {
        log.info("[CustomerController] createCustomerForm() called");

        model.addAttribute("customer", new CreateCustomerRequest());
        return "customerCreateForm";
    }

//    @PostMapping("/new")
//    public String createCustomer(CreateCustomerRequest createCustomerRequest) {
//        customerService.createCustomer(createCustomerRequest.email(), createCustomerRequest.name());
//        return "redirect:/customers";
//    }
}