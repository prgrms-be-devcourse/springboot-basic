package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.customer.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CustomerController {

    @GetMapping("/customers")
    public String viewCustomersPage() {
        return "views/customers";
    }
}
