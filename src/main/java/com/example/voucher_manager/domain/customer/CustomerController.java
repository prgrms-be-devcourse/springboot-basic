package com.example.voucher_manager.domain.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // VIEWS
    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<CustomerDto> convertObject = customerService.findAll().stream()
                .map(CustomerDto::from)
                .toList();
        model.addAttribute("customers", convertObject);
        return "views/customer/customers"; // 아래 작업을 이렇게도 변환 가능.
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        var findCustomer = customerService.findCustomer(customerId);

        if (findCustomer.isPresent()) {
            model.addAttribute("customer", CustomerDto.from(findCustomer.get()));
            return "views/customer/customer-details";
        }
        return "views/error/404";
    }

    @PostMapping("/customers/{customerId}")
    public String modifyCustomer(CustomerDto customerDto) {
        logger.info("Got customer modify profile request {}", customerDto);
        customerService.modifyProfile(CustomerDto.toEntity(customerDto));
        return "redirect:/customers";
    }

    @GetMapping("/customers/signup")
    public String viewSignupPage() {
        return "views/customer/customer-signup";
    }

    @PostMapping("/customers/signup")
    public String saveCustomer(CreateCustomerRequest createCustomerRequest) {
        logger.info("Got customer signup request {}", createCustomerRequest);
        customerService.signUp(createCustomerRequest.name(), createCustomerRequest.email());
        return "redirect:/customers";
    }

    @GetMapping("/customers/unregister/{customerId}")
    public String unregisterCustomer(@PathVariable UUID customerId) {
        logger.info("Got customer unregister request {}", customerId);
        if (customerService.deleteCustomer(customerId)) {
            return "redirect:/customers";
        }
        return "views/error/504";
    }
}
