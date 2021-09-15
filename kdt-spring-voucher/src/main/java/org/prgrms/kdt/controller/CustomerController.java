package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.CreateCustomerRequest;
import org.prgrms.kdt.domain.CustomerDto;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<CustomerEntity> allCustomers = customerService.getAllCustomers();

        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("customers", allCustomers);

        return "views/customers";
    }

    @GetMapping("/main")
    public String viewMainPage() {
        return "views/main";
    }


    @GetMapping("/customers/{customerId}/detail")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<CustomerEntity> maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/customer-details";
        } else {
            return "views/404";
        }
    }

    @PostMapping("/customers/{customerId}/remove")
    public String deleteCustomer(@PathVariable("customerId") UUID customerId){
        logger.info("삭제할 customerid : {}",customerId);
        System.out.println("삭제할 customerid : "+customerId);
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }

    @PostMapping("/customers/{customerId}/detail")
    public String editCustomer(@PathVariable("customerId") UUID customerId, CreateCustomerRequest createCustomerRequest){
        CustomerDto updateCustomer = CustomerDto.builder().customerId(customerId)
                .name(createCustomerRequest.getName())
                .email(createCustomerRequest.getEmail())
                .build();
        customerService.updateCustomer(updateCustomer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage() {
        return "views/items/customerForm";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.getEmail(), createCustomerRequest.getName());
        return "redirect:/customers";
    }

}
