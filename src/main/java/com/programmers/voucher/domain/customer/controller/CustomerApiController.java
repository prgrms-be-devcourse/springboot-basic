package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.constant.BaseResponse;
import com.programmers.voucher.domain.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerApiController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<CustomerResponse> createCustomer(@Validated @RequestBody CustomerCreateRequest request) {
        CustomerResponse customer = customerService.createCustomer(request);
        return BaseResponse.created(customer);
    }

    @GetMapping
    public BaseResponse<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return BaseResponse.ok(customers);
    }

    @GetMapping("/{customerId}")
    public BaseResponse<CustomerResponse> getCustomer(@PathVariable UUID customerId) {
        CustomerResponse customer = customerService.getCustomer(customerId);
        return BaseResponse.ok(customer);
    }

    @PostMapping("/{customerId}")
    public BaseResponse<Object> deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return BaseResponse.ok();
    }
}
