package org.programmers.springorder.customer.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerPageController {
    private final Console console;

    private final CustomerService customerService;

    public CustomerPageController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String getCustomerListPage(Model model){
        List<CustomerResponseDto> allCustomers = customerService.getAllCustomers();
        model.addAttribute("customerList", allCustomers);
        return "customers";
    }

}
