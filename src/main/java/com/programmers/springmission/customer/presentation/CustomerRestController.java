package com.programmers.springmission.customer.presentation;

import com.programmers.springmission.customer.application.CustomerService;
import com.programmers.springmission.customer.presentation.request.CustomerCreateRequest;
import com.programmers.springmission.customer.presentation.request.CustomerUpdateRequest;
import com.programmers.springmission.customer.presentation.response.CustomerResponse;
import com.programmers.springmission.customer.presentation.response.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerCreateRequest));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findByIdCustomer(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(customerService.findByIdCustomer(customerId));
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findByEmailCustomer(@RequestParam String email) {
        return ResponseEntity.ok(customerService.findByEmailCustomer(email));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerResponse>> findAllCustomer() {
        return ResponseEntity.ok(customerService.findAllCustomer());
    }

    @PutMapping("/name/{customerId}")
    public ResponseEntity<CustomerResponse> updateName(@PathVariable("customerId") UUID customerId,
                                                        @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return ResponseEntity.ok(customerService.updateName(customerId, customerUpdateRequest));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/list")
    public ResponseEntity<Void> deleteAllCustomer() {
        customerService.deleteAllCustomer();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/wallet/{customerId}")
    public ResponseEntity<List<WalletResponse>> findWallet(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(customerService.findWallet(customerId));
    }
}
