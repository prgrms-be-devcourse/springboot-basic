package org.prgrms.kdt.customer.controller;

import java.util.UUID;
import org.prgrms.kdt.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customers")
    public String customerList(Model model) {
        var allCustomers = customerService.getAllCustomers();
        model.addAttribute("customers", allCustomers);
        return "customers";
    }

    @GetMapping("/customers/{customerId}")
    public String customerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        var customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return "customer-detail";
    }

    @PostMapping("/customers/{customerId}")
    public String customerUpdate(
        @PathVariable("customerId") UUID customerId,
        @ModelAttribute CustomerDto customerDto) {
        customerService.updateCustomer(customerId, customerDto);
        return "redirect:/customers";
    }


    @GetMapping("/customers/new")
    public String viewNewCustomerPage() {
        return "customer-new";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(@ModelAttribute CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return "redirect:/customers";
    }

    @PostMapping("/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }

}
