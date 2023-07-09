package com.devcourse.voucherapp.controller;

import com.devcourse.voucherapp.entity.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.CustomerResponseDto;
import com.devcourse.voucherapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public CustomerResponseDto create(CustomerCreateRequestDto request) {
        return customerService.create(request);
    }
}
