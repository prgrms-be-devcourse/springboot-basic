package org.prgms.kdtspringvoucher.customer.controller;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.dto.CreateCustomerRequest;
import org.prgms.kdtspringvoucher.customer.dto.UpdateCustomerRequest;
import org.prgms.kdtspringvoucher.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //customer main page
    @GetMapping
    public String getCustomerMain() {
        return "customer/customer-main";
    }

    //customer list page
    @GetMapping("list")
    public String getCustomers(Model model) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }

    //customer create page
    @GetMapping("new")
    public String createNewCustomer() {
        return "customer/customer-new";
    }

    @PostMapping("new")
    public String createNewCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getEmail(), createCustomerRequest.getCustomerType());
        return "redirect:/customer/" + customer.getCustomerId();
    }

    //customer detail page
    @GetMapping("{customerId}")
    public String getCustomerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "customer/customer-detail";
    }

    //customer update page
    @GetMapping("update/{customerId}")
    public String updateCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "customer/customer-update";
    }

    @PostMapping("update/{customerId}")
    public String updateCustomer(@PathVariable("customerId") UUID customerId, UpdateCustomerRequest updateCustomerRequest) {
        customerService.updateCustomerById(customerId, updateCustomerRequest.getName(), updateCustomerRequest.getCustomerType());
        return "redirect:/customer/list";
    }

    //customer delete
    @GetMapping("delete/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:/customer/list";
    }
}
