package com.pgms.part1.domain.customer.controller;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerWebController {

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String getCustomersList(Model model){
        List<CustomerResponseDto> customerResponseDtos = customerService.listCustomers();
        model.addAttribute("customers", customerResponseDtos);
        return "customersListPage";
    }

    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable Long id){

        return "redirect:/customers";
    }

    @PostMapping("/customers")
    public String createCustomer(){

        return "redirect:/customers";
    }
}
