package org.programmers.springorder.customer.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.customer.dto.CustomerRequestDto;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/new-customer")
    public String getNewCustomerPage(Model model){
        return "new-customer";
    }

    @PostMapping("/customers")
    public String enrollCustomer(CustomerRequestDto requestDto){
        customerService.newCustomer(requestDto);
        return "redirect:/customers";
    }

}
