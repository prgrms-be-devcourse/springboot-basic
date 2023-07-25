package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.service.CustomerService;
import com.programmers.springweekly.util.validator.CustomerValidator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@Validated CustomerCreateRequest customerCreateRequest) {
        CustomerValidator.validateCustomer(
                customerCreateRequest.getCustomerName(),
                customerCreateRequest.getCustomerEmail(),
                customerCreateRequest.getCustomerType()
        );

        CustomerResponse customerResponse = customerService.save(customerCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getFindAll() {
        CustomerListResponse customerListResponse = customerService.findAll();

        return ResponseEntity.ok(customerListResponse.getCustomerList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("id") UUID customerId) {
        CustomerResponse customerResponse = customerService.findById(customerId);

        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID customerId) {
        boolean isExistCustomerId = customerService.existById(customerId);

        if (!isExistCustomerId) {
            throw new NoSuchElementException("사용자가 삭제하려는 아이디 " + customerId + "는 없는 ID입니다.");
        }

        customerService.deleteById(customerId);

        return ResponseEntity.noContent().build();
    }

}
