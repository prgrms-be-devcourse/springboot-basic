package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CustomerApiController {
    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> findCustomers() {
        List<CustomerDto> customers = customerService.findCustomers();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDto> findCustomer(@PathVariable UUID customerId) {
        CustomerDto customer = customerService.findCustomer(customerId);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<UUID> createCustomer(@RequestBody CustomerCreateRequest request) {
        UUID customerId = customerService.createCustomer(request.getEmail(), request.getName());
        URI location = URI.create("/api/v1/customers/" + customerId);
        return ResponseEntity.created(location).body(customerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/customers/{customerId}")
    public void updateCustomer(@PathVariable UUID customerId,
                               @RequestBody CustomerUpdateRequest request) {
        customerService.updateCustomer(customerId, request.getName(), request.isBanned());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/customers/delete")
    public void deleteCustomer(@RequestBody UUID customerId) {
        customerService.deleteCustomer(customerId);
    }
}
