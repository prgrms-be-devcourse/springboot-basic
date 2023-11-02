package com.zerozae.voucher.controller.api;

import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class ApiCustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        return ResponseEntity.status(CREATED).body(customerService.createCustomer(customerCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return ResponseEntity.status(OK).body(customerService.findAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable("customerId") String customerId) {
        validateInputUuid(customerId);
        return ResponseEntity.status(OK).body(customerService.findById(UUID.fromString(customerId)));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable("customerId") String customerId,
                                         @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        validateInputUuid(customerId);
        customerService.update(UUID.fromString(customerId), customerUpdateRequest);
        return ResponseEntity.status(OK).body("완료 되었습니다.");
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") String customerId) {
        validateInputUuid(customerId);
        customerService.deleteById(UUID.fromString(customerId));
        return ResponseEntity.status(OK).body("완료 되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCustomers() {
        customerService.deleteAll();
        return ResponseEntity.status(OK).body("완료 되었습니다.");

    }
}
