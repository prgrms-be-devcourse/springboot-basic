package com.programmers.springbootbasic.domain.customer.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.customer.dto.CustomerRequestDto;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.service.CustomerService;
import com.programmers.springbootbasic.domain.customer.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    public CommonResult<String> createCustomer(String email, String name) {
        return execute(customerService::createCustomer, CustomerRequestDto.builder()
                .email(Email.from(email).getValue())
                .name(name)
                .build());
    }

    public CommonResult<String> addCustomerInBlacklist(String email) {
        return execute(customerService::addCustomerInBlacklist, CustomerRequestDto.builder()
                .email(Email.from(email).getValue())
                .build());
    }

    public CommonResult<String> removeCustomerInBlacklist(String email) {
        return execute(customerService::removeCustomerFromBlacklist, CustomerRequestDto.builder()
                .email(Email.from(email).getValue())
                .build());
    }

    public CommonResult<List<String>> findAllCustomer() {
        return CommonResult.getListResult(
                customerService.findAllCustomer()
                        .stream()
                        .map(Customer::getInformation)
                        .toList()
        );
    }

    public CommonResult<List<String>> findAllBlacklistCustomer() {
        return CommonResult.getListResult(
                customerService.findAllBlacklistCustomer()
                        .stream()
                        .map(Customer::getInformation)
                        .toList()
        );
    }

    public CommonResult<String> deleteAllCustomer() {
        customerService.deleteAllCustomer();
        return CommonResult.getSuccessResult();
    }

    private CommonResult<String> execute(Consumer<CustomerRequestDto> consumer, CustomerRequestDto customerRequestDto) {
        try {
            consumer.accept(customerRequestDto);
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

}
