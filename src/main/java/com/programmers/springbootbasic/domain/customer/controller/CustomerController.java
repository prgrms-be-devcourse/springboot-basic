package com.programmers.springbootbasic.domain.customer.controller;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.model.ListResult;
import com.programmers.springbootbasic.common.response.service.ResponseFactory;
import com.programmers.springbootbasic.domain.customer.dto.CustomerRequestDto;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static void verifyEmail(String email) throws IllegalArgumentException {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
        }
    }

    public CommonResult createCustomer(String email, String name) {
        try {
            verifyEmail(email);
            customerService.createCustomer(CustomerRequestDto.builder()
                    .email(email)
                    .name(name)
                    .build());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public CommonResult addCustomerInBlacklist(String email) {
        try {
            verifyEmail(email);
            customerService.addCustomerInBlacklist(CustomerRequestDto.builder()
                    .email(email)
                    .build());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public CommonResult removeCustomerInBlacklist(String email) {
        try {
            verifyEmail(email);
            customerService.removeCustomerFromBlacklist(CustomerRequestDto.builder()
                    .email(email)
                    .build());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public ListResult<String> findAllCustomer() {
        return ResponseFactory.getListResult(
                customerService.findAllCustomer()
                        .stream()
                        .map(Customer::getInformation)
                        .toList()
        );
    }

    public ListResult<String> findAllBlacklistCustomer() {
        return ResponseFactory.getListResult(
                customerService.findAllBlacklistCustomer()
                        .stream()
                        .map(Customer::getInformation)
                        .toList()
        );
    }

    public CommonResult deleteAllCustomer() {
        customerService.deleteAllCustomer();
        return ResponseFactory.getSuccessResult();
    }
}
