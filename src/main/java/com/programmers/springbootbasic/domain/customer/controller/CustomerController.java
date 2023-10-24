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

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    public CommonResult<String> createCustomer(String email, String name) {
        try {
            customerService.createCustomer(CustomerRequestDto.builder()
                    .email(Email.from(email).getValue())
                    .name(name)
                    .build());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

    public CommonResult<String> addCustomerInBlacklist(String email) {
        try {
            customerService.addCustomerInBlacklist(CustomerRequestDto.builder()
                    .email(Email.from(email).getValue())
                    .build());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

    public CommonResult<String> removeCustomerInBlacklist(String email) {
        try {
            customerService.removeCustomerFromBlacklist(CustomerRequestDto.builder()
                    .email(Email.from(email).getValue())
                    .build());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
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
}
