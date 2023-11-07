package team.marco.voucher_management_system.web_app.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.service.CustomerService;
import team.marco.voucher_management_system.web_app.dto.CreateCustomerRequest;

@Controller
@RequestMapping(path = "/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String viewCustomers(Model model) {
        List<Customer> customers = customerService.getCustomers();

        model.addAttribute("customers", customers);

        return "views/customers";
    }

    @GetMapping("/new")
    public String viewNewCustomer() {
        return "views/new-customer";
    }

    @PostMapping("/new")
    public String createCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.create(createCustomerRequest.name(), createCustomerRequest.email());

        return "redirect:/customers";
    }
}
