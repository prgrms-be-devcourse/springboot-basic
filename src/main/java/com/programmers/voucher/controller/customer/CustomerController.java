package com.programmers.voucher.controller.customer;

import com.programmers.voucher.controller.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String create() {
        return "customer/customer_new";
    }

    @PostMapping
    public String create(CustomerCreateRequest customerCreateRequest) {
        customerService.create(customerCreateRequest);
        return "redirect:/vouchers";
    }
}
