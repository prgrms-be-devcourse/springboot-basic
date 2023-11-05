package org.programmers.springorder.controller.customer;

import org.programmers.springorder.dto.customer.CustomerRequestDto;
import org.programmers.springorder.dto.customer.CustomerResponseDto;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getCustomers(Model model) {
        List<CustomerResponseDto> customers = customerService.getAllCustomer();
        model.addAttribute("customers", customers);
        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable UUID id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/customer";
    }

    @GetMapping("/new-customer")
    public String newCustomer() {
        return "customer/addForm";
    }

    @PostMapping("/new-customer")
    public String newCustomer(@ModelAttribute CustomerRequestDto request) {
        customerService.createCustomer(request);
        return "redirect:/customers";
    }

    @PostMapping("/{id}")
    public String deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

}
