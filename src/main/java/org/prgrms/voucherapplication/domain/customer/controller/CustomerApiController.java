package org.prgrms.voucherapplication.domain.customer.controller;

import org.prgrms.voucherapplication.domain.customer.controller.dto.CustomerDto;
import org.prgrms.voucherapplication.domain.customer.entity.Customer;
import org.prgrms.voucherapplication.domain.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CustomerApiController {

    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    @ResponseBody
    public List<Customer> findCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/v1/customers/{customerId}")
    @ResponseBody
    public ResponseEntity<Customer> findCustomer(@PathVariable("customerId") UUID customerId) {
        Optional<Customer> customer = customerService.getCustomer(customerId);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("api/v1/customers/{customerId}")
    @ResponseBody
    public CustomerDto saveCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer) {
        Customer to = CustomerDto.to(customer);
        customerService.createCustomer(to.getEmail(), to.getName());
        return customer;
    }
}
