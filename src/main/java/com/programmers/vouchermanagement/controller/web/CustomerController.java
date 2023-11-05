package com.programmers.vouchermanagement.controller.web;

import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showAllCustomers(Model model) {
        final List<CustomerDto.Response> customers = customerService.findAllCustomers().stream()
                .map(CustomerDto.Response::new).toList();
        model.addAttribute("customers", customers);
        return "/customer/customer_detail";
    }

    @GetMapping("/new")
    public String deleteCustomer() {
        return "/customer/add_customer_form";
    }

    @PostMapping("/new")
    public String createVoucher(@ModelAttribute CustomerDto.CreateRequest customerDto) {
        customerService.createCustomer(customerDto);
        return "redirect:/customer";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> showAddCustomerForm(@RequestParam String customerId) {
        customerService.deleteCustomer(UUID.fromString(customerId));
        return ResponseEntity.ok().build();
    }
}
