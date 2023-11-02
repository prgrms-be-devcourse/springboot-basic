package com.prgrms.springbasic.domain.customer.controller;

import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerWebController {

    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<CustomerResponse> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "/customer/list";
    }

    @GetMapping("/create-form")
    public String createForm() {
        return "customer/create_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreateCustomerRequest request) {
        customerService.createCustomer(request);
        return "redirect:/customers/list";
    }
}
