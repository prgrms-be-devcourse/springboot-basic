package com.zerozae.voucher.controller.api;

import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class ApiCustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        return new ResponseEntity(customerService.createCustomer(customerCreateRequest), CREATED);
    }

    @GetMapping
    public ResponseEntity findAllCustomers() {
        return new ResponseEntity(customerService.findAllCustomers(), OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity findCustomerById(@PathVariable("customerId") String customerId) {
        validateInputUuid(customerId);
        return new ResponseEntity(customerService.findById(UUID.fromString(customerId)), OK);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId") String customerId,
                                         @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        validateInputUuid(customerId);
        customerService.update(UUID.fromString(customerId), customerUpdateRequest);
        return ResponseEntity.ok("완료 되었습니다.");
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") String customerId) {
        validateInputUuid(customerId);
        customerService.deleteById(UUID.fromString(customerId));
        return ResponseEntity.ok("완료 되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity deleteAllCustomers() {
        customerService.deleteAll();
        return ResponseEntity.ok("완료 되었습니다.");
    }
}
