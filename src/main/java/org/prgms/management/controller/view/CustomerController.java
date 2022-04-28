package org.prgms.management.controller.view;

import org.prgms.management.service.customer.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/customers"})
    public String customersPage(Model model) {
        var customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }

    @GetMapping("/customer/{id}")
    public String customerPage(Model model, @PathVariable("id") UUID id) {
        var customer = customerService.getById(id);
        if (customer.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("customer", customer.get());
        return "customer/customer-detail";
    }
}
