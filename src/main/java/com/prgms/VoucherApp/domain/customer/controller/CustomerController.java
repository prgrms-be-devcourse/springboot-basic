package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
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
        CustomerResponse customerResponse = customerService.findOne(inputCustomerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id가 입력되었습니다."));
        model.addAttribute("customer", customerResponse);
        return "customer/customer";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customerStatus", new CustomerCreateRequest());
        return "customer/add_customer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute CustomerCreateRequest customerCreateRequest) {
        customerService.save(customerCreateRequest);
        return "redirect:/customers";
    }

}
