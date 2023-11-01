package com.programmers.vouchermanagement.controller.rest;

import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class RestCustomerController {
    private final CustomerService customerService;

    public RestCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto.Response>> showAllCustomers() {
        final List<CustomerDto.Response> customers = customerService.findAllCustomers().stream()
                .map(CustomerDto.Response::new).toList();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto.Response> searchVoucher(@PathVariable String customerId) {
        CustomerDto.Response customer = new CustomerDto.Response(customerService.findCustomerById(UUID.fromString(customerId)));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerDto.Response> createVoucher(@RequestBody CustomerDto.CreateRequest customerDto) {
        CustomerDto.Response customer = new CustomerDto.Response(customerService.createCustomer(customerDto));
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> showAddCustomerForm(@RequestParam String customerId) {
        customerService.deleteCustomer(UUID.fromString(customerId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
