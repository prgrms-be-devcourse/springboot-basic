package com.voucher.vouchermanagement.controller.api.v1;

import com.voucher.vouchermanagement.controller.api.v1.request.CreateCustomerRequest;
import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.service.customer.CustomerService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseEntity<UUID> join(@RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(customerService.join(request.getName(), request.getEmail()));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    public ResponseEntity<List<CustomerDto>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }


}

