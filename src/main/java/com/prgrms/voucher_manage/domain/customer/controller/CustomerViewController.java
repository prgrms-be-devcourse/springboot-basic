package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerViewController {
    private final CustomerService customerService;
    @GetMapping("customers/new")
    public String createForm(){
        return "customer/createCustomer";
    }

    @PostMapping("customers/new")
    public String createCustomer(@ModelAttribute CreateCustomerReq dto){
        customerService.save(dto);
        return "redirect:/customers/get";
    }

    @GetMapping("customers/get")
    public String getAllCustomers(Model model){
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer/customerList";
    }

    @GetMapping("customers/get/black")
    public String getBlackCustomers(Model model){
        List<Customer> blackCustomers = customerService.getBlackCustomers();
        model.addAttribute("blackCustomers", blackCustomers);
        return "customer/blackCustomerList";
    }

    @GetMapping("customers/get/{customerId}")
    public String getCustomer(@PathVariable UUID customerId, Model model){
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        return "customer/getCustomer";
    }
}
