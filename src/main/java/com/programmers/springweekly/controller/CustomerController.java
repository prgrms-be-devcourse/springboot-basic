package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.service.CustomerService;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public Map<UUID, Customer> getBlackList() {
        return customerService.getBlackList();
    }
}
