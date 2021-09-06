package org.prgms.w3d1.controller;

import org.prgms.w3d1.model.customer.CreateCustomerRequest;
import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.customer.UpdateCustomerRequest;
import org.prgms.w3d1.service.CustomerService;
import org.prgms.w3d1.service.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        var allCustomers = customerService.findAll();
        model.addAttribute("customers", allCustomers);
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage() {
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    // 왜 record가 들어가면 매칭이 되는거냐고;;
    public String createNewCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.name(), createCustomerRequest.email());
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String viewCustomerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        var maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/customer-details";
        } else {
            return "views/404";
        }
    }

    @GetMapping("/customers/update")
    public String viewCustomerUpdate(@RequestParam(value = "id") UUID customerId, Model model) {
        var maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/update-customer";
        } else {
            return "views/404";
        }
    }

    @PostMapping("/customers/update")
    public String updateCustomer(
        @RequestParam(value = "id") UUID customerId,
        UpdateCustomerRequest updateCustomerRequest, Model model)
    {
        var updatedCustomer = customerService.updateCustomerByNameAndEmail(
            customerId, updateCustomerRequest.name(), updateCustomerRequest.email());
        model.addAttribute("customer", updatedCustomer);
        return "views/customer-details";
    }
}
