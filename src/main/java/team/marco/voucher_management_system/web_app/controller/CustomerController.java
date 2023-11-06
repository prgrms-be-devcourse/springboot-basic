package team.marco.voucher_management_system.web_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import team.marco.voucher_management_system.service.CustomerService;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage() {
        return "views/customers";
    }
}
