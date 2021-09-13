package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.dto.CustomerDto;
import org.prgrms.kdt.service.CustomerService;
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
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //조회페이지
    @GetMapping("/customers")
    public String list(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String create(){
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CustomerDto customerDto){
        customerService.createCustomer(customerDto.email(), customerDto.name());
        return "redirect:/customers";
    }

}
