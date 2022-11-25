package com.programmers.customer.controller.api;

import com.programmers.customer.Customer;
import com.programmers.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiCustomerController {
    private final CustomerService customerService;

    public ApiCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/customers")
    @ResponseBody
    public ResponseEntity<List<Customer>> findCustomers() {

        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
}
