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
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/api/v1/customers")
    @ResponseBody
    public List<CustomerEntity> restFindCustomers() {
        List<CustomerEntity> allCustomers = customerService.getAllCustomers();
        return allCustomers;
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<CustomerEntity> maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/customer-details";
        } else {
            return "views/404";
        }
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage() {
        return "views/new-customers";
    }

    @PostMapping("/api/v1/customers/{customerId}")
    @ResponseBody
    public CustomerEntity saveCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer) {
        logger.info("Got customer save request {}", customer);
        return CustomerDto.to(customer);
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.getEmail(), createCustomerRequest.getName());
        return "redirect:/customers";
    }

    //delete 기능
    @DeleteMapping("/api/v1/customers/{customerId}")
    public void removeCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomer(customerId);
    }

    //update 기능
    @PutMapping("/api/vi/customers/{customerId}")
    public CustomerEntity updateCustomer(@PathVariable("customerId") UUID customerid, String email, String name){
        var customer = CustomerDto.builder()
                .customerId(customerid)
                .email(email)
                .name(name)
                .build();
        var updateCustomer = customerService.updateCustomer(customer);
        return updateCustomer.get();
    }

}
