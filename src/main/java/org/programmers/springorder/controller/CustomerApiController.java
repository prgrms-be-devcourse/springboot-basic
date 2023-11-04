package org.programmers.springorder.controller;

import org.programmers.springorder.dto.customer.CustomerRequestDto;
import org.programmers.springorder.dto.customer.CustomerResponseDto;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerApiController {

    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> findAllCustomer() {
        List<CustomerResponseDto> customers = customerService.getAllCustomer();
        return ResponseEntity.ok()
                .body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findCustomer(@PathVariable UUID id) {
        Customer customer = customerService.findById(id);
        CustomerResponseDto response = CustomerResponseDto.of(customer);
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> addCustomer(@RequestBody CustomerRequestDto request) {
        Customer savedCustomer = customerService.createCustomer(request);
        CustomerResponseDto response = CustomerResponseDto.of(savedCustomer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
