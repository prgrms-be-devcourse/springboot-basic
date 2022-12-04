package org.prgms.springbootbasic.controller;


import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerCreateDTO;
import org.prgms.springbootbasic.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String customerList(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer";
    }

    @GetMapping("/customers/new")
    public String createCustomerForm() {
        return "create_customer";
    }

    @PostMapping("/customers/new")
    public String createCustomer(CustomerCreateDTO customerCreateDTO) {
        customerService.createCustomer(customerCreateDTO);
        return "redirect:/customers";
    }

    public void updateLastLoginTime(Customer customer) {
        customerService.updateLastLoginAt(customer);
    }

    @GetMapping("customers/detail/{customerId}")
    public String findOneCustomer(@PathVariable UUID customerId, Model model) {
        Customer customer = customerService.findOneCustomer(customerId).orElseThrow(NoSuchElementException::new);
        this.updateLastLoginTime(customer);
        model.addAttribute("customer", customer);
        return "customer_detail";
    }

    @GetMapping("customers/{voucherId}")
    public String findCustomers(@PathVariable UUID voucherId, Model model) {
        model.addAttribute("customers", customerService.findCustomers(voucherId));
        return "voucher_detail";
    }

    @DeleteMapping("/customers/detail/{customerId}")
    public String deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(UUID.fromString(customerId));
        return "redirect:/customers";
    }
}
