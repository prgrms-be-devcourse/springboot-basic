package org.prgms.voucheradmin.domain.customer.controller;

import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.dto.CustomerCreateReqDto;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerViewController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerViewController.class);

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        logger.error(ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "views/error";
    }

    @PostMapping("/customers/new")
    public String addNewVoucher(CustomerCreateReqDto customerCreateReqDto) {
        customerService.createCustomer(customerCreateReqDto);
        return "redirect:/customers";
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<Customer> allCustomers = customerService.getCustomers();
        model.addAttribute("customers", allCustomers);
        return "views/customer/customers";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomersPage() {
        return "views/customer/new-customer";
    }

    @GetMapping("/customers/{customerId}")
    public String viewCustomersPage(@PathVariable UUID customerId, Model model) {
        Customer customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return "views/customer/customer";
    }

}
