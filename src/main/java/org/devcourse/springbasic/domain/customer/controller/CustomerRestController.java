package org.devcourse.springbasic.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.devcourse.springbasic.domain.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerDto.Response>> findByCriteria(
            @RequestBody CustomerDto.Request request
    ) {
        List<CustomerDto.Response> customers = customerService.findByCriteria(request);
        return ResponseEntity.ok(customers);
    }

    @PostMapping()
    public ResponseEntity<UUID> createNewCustomer(
            @RequestBody @Valid CustomerDto.SaveRequest request
    ) {
        UUID savedCustomerId = customerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomerId);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto.Response> findById(
            @PathVariable UUID customerId
    ) {
        CustomerDto.Response response = customerService.findById(customerId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping()
    public ResponseEntity<UUID> update(
            @RequestBody CustomerDto.UpdateRequest request
    ) {
        UUID updatedCustomerId = customerService.update(request);
        return ResponseEntity.ok(updatedCustomerId);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<UUID> deleteById(
            @PathVariable UUID customerId
    ) {
        customerService.deleteById(customerId);
        return ResponseEntity.ok(customerId);
    }
}