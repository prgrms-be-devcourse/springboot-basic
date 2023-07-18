package com.devcourse.voucherapp.controller.console;

import com.devcourse.voucherapp.entity.customer.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Profile("local")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerResponseDto create(CustomerCreateRequestDto request) {
        return customerService.create(request);
    }

    public List<CustomerResponseDto> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    public CustomerResponseDto findCustomerByNickname(String nickname) {
        return customerService.findCustomerByNickname(nickname);
    }

    public CustomerResponseDto update(CustomerUpdateRequestDto request) {
        return customerService.update(request);
    }

    public void deleteByNickname(String nickname) {
        customerService.deleteByNickname(nickname);
    }

    public List<CustomerResponseDto> findBlackListCustomers() {
        return customerService.findBlackListCustomers();
    }
}
