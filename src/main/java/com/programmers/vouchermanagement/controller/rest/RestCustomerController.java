package com.programmers.vouchermanagement.controller.rest;

import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.programmers.vouchermanagement.controller.rest.ApiEndpoints.REST_CUSTOMER_BASE_URI;

@RestController
@RequestMapping(REST_CUSTOMER_BASE_URI)
public class RestCustomerController {
    private final CustomerService customerService;

    public RestCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto.Response>> showAllCustomers() {
        final List<CustomerDto.Response> customers = customerService.findAllCustomers().stream()
                .map(CustomerDto.Response::new).toList();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<CustomerDto.Response> createCustomer(@RequestBody CustomerDto.CreateRequest customerDto,
                                                               UriComponentsBuilder uriComponentsBuilder) {
        CustomerDto.Response customer = new CustomerDto.Response(customerService.createCustomer(customerDto));
        URI location = uriComponentsBuilder.path(REST_CUSTOMER_BASE_URI + "/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@RequestParam String customerId) {
        customerService.deleteCustomer(UUID.fromString(customerId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto.Response> searchCustomer(@PathVariable String customerId) {
        CustomerDto.Response customer = new CustomerDto.Response(customerService.findCustomerById(UUID.fromString(customerId)));
        return ResponseEntity.ok(customer);
    }
}
