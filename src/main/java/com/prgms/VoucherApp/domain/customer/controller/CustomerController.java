package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String customers(Model model) {
        CustomersResponse response = customerService.findAll();
        List<CustomerResponse> customers = response.customers();
        model.addAttribute("customers", customers);
        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String customer(@PathVariable String id, Model model) {
        UUID inputCustomerId = UUID.fromString(id);
        CustomerResponse customerResponse = customerService.findOne(inputCustomerId);
        model.addAttribute("customer", customerResponse);
        return "customer/customer";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customerStatus", new CustomerCreateRequest());
        model.addAttribute("customerStatusList", CustomerStatus.values());
        return "customer/add_customer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute CustomerCreateRequest customerCreateRequest) {
        customerService.save(customerCreateRequest);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String editCustomerForm(@PathVariable String id, Model model) {
        UUID inputCustomerId = UUID.fromString(id);
        CustomerResponse findCustomer = customerService.findOne(inputCustomerId);
        model.addAttribute("customer", findCustomer);
        model.addAttribute("customerStatusList", CustomerStatus.values());
        return "customer/edit_customer";
    }

    @PostMapping("/{id}/edit")
    public String editCustomer(@PathVariable String id, @ModelAttribute CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(customerUpdateRequest);
        return "redirect:/customers/{id}";
    }

    @GetMapping("/{id}/delete")
    public String deleteVoucher(@PathVariable String id) {
        UUID inputCustomerId = UUID.fromString(id);
        customerService.deleteById(inputCustomerId);
        return "redirect:/customers";
    }

}
