package com.programmers.kdtspringorder.customer.controller;

import com.programmers.kdtspringorder.customer.model.Customer;
import com.programmers.kdtspringorder.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> findCustomers(Model model) {
        return customerService.findAll();
    }

    @GetMapping("/api/v1/customers/{customerId}")
    public ResponseEntity<Customer> findCustomerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        return optionalCustomer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
