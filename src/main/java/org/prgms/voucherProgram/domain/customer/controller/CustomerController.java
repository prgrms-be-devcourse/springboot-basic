package org.prgms.voucherProgram.domain.customer.controller;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Customer;
import org.prgms.voucherProgram.domain.customer.domain.Email;
import org.prgms.voucherProgram.domain.customer.dto.CustomerRequest;
import org.prgms.voucherProgram.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<Customer> customers = customerService.findCustomers();
        model.addAttribute("customers", customers);
        return "views/customer/customers";
    }

    @GetMapping("customers/new")
    public String viewNewCustomer() {
        return "views/customer/new-customers";
    }

    @PostMapping("/customers/new")
    public String joinCustomer(CustomerRequest customerRequest) {
        customerService.join(customerRequest);
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        return "views/customer/customer-details.html";
    }

    @PostMapping("/customers/update/{email}")
    public String updateCustomer(@PathVariable("email") Email email, CustomerRequest customerRequest) {
        customerService.modify(email, customerRequest);
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{email}")
    public String updateCustomer(@PathVariable("email") Email email) {
        customerService.delete(email);
        return "redirect:/customers";
    }
}
