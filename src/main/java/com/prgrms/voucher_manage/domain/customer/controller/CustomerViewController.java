package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerDto;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerViewController {
    private final CustomerService customerService;
    @GetMapping("customer/new")
    public String createForm(){
        return "customer/createCustomer";
    }

    @PostMapping("customer/new")
    public String createCustomer(@ModelAttribute CreateCustomerDto dto){
        customerService.save(dto);
        return "redirect:/customer/list";
    }

    @GetMapping("customer/list")
    public String getAllCustomers(Model model){
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer/customerList";
    }

    @GetMapping("customer/list/black")
    public String getBlackCustomers(Model model){
        List<Customer> blackCustomers = customerService.getBlackCustomers();
        model.addAttribute("blackCustomers", blackCustomers);
        return "customer/blackCustomerList";
    }
}
