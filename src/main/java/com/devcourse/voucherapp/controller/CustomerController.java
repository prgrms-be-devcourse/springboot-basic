package com.devcourse.voucherapp.controller;

import com.devcourse.voucherapp.entity.customer.request.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.response.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.request.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.entity.customer.response.CustomersResponseDto;
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

    public CustomersResponseDto findAllCustomers() {
        return customerService.findAllCustomers();
    }

    public CustomerResponseDto update(CustomerUpdateRequestDto request) {
        return customerService.update(request);
    }

    public void deleteByNickname(String nickname) {
        customerService.deleteByNickname(nickname);
    }

    public CustomersResponseDto findBlackListCustomers() {
        return customerService.findBlackListCustomers();
    }
}
