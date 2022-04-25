package org.prgms.voucherProgram.domain.customer.controller;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Email;
import org.prgms.voucherProgram.domain.customer.dto.CustomerDto;
import org.prgms.voucherProgram.domain.customer.dto.CustomerRequest;
import org.prgms.voucherProgram.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String viewCustomersPage(Model model) {
        List<CustomerDto> customers = customerService.findCustomers().stream()
            .map(CustomerDto::from)
            .toList();
        model.addAttribute("customers", customers);
        return "/customer/customers";
    }

    @GetMapping("/new")
    public String viewNewCustomer() {
        return "customer/new-customers";
    }

    @PostMapping("/new")
    public String joinCustomer(CustomerRequest customerRequest) {
        customerService.join(customerRequest);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        CustomerDto customerDto = CustomerDto.from(customerService.findById(customerId));
        model.addAttribute("customer", customerDto);
        return "/customer/customer-details";
    }

    @PostMapping("/update/{email}")
    public String updateCustomer(@PathVariable("email") Email email, CustomerRequest customerRequest) {
        customerService.modify(email, customerRequest);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{email}")
    public String deleteCustomer(@PathVariable("email") Email email) {
        customerService.delete(email);
        return "redirect:/customers";
    }

    @ExceptionHandler
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
