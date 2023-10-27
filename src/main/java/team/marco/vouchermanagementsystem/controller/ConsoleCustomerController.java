package team.marco.vouchermanagementsystem.controller;

import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.service.CustomerService;

@Controller
public class ConsoleCustomerController {
    private final CustomerService customerService;

    public ConsoleCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void selectCommand() {
        
    }
}
