package org.programmers.kdtspring.controller.api;

import org.programmers.kdtspring.dto.CustomerDTO;
import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findCustomers() {


        return null;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer) {

        return null;
    }

    @GetMapping("/{customerId}")
    @CrossOrigin("*")
    public ResponseEntity<Customer> findCustomer(@PathVariable("customerId") UUID customerId) {

        var customer = customerService.getCustomerById(customerId);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}