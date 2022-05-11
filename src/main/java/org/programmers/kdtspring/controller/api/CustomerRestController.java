package org.programmers.kdtspring.controller.api;

import org.programmers.kdtspring.controller.web.CustomerController;
import org.programmers.kdtspring.dto.CustomerDTO;
import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findCustomers() {
        log.info("[CustomerRestController] findCustomers() called");

        return customerService.getAllCustomers();
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customer) {
        log.info("[CustomerRestController] saveCustomer() called");
        log.info("Got customer save request {}", customer);
        return customer;
    }

    @GetMapping("/{customerId}")
    @CrossOrigin("*")
    public ResponseEntity<Customer> findCustomer(@PathVariable("customerId") UUID customerId) {
        log.info("[CustomerRestController] findCustomer() called");

        var customer = customerService.getCustomerById(customerId);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}