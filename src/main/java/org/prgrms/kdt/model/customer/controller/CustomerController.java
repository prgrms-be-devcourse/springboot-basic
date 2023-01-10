package org.prgrms.kdt.model.customer.controller;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String customerListsPage(Model model) {
        List<Customer> allCustomers = customerService.getAllCustomers();
        model.addAttribute("customers", allCustomers);
        return "/customer/customer-lists";
    }

    @GetMapping("/blacklists")
    public String blacklistsPage(Model model) {
        List<Customer> allBlacklist = customerService.getAllBlacklist();
        model.addAttribute("blacklists", allBlacklist);
        return "/customer/blacklists";
    }
}
