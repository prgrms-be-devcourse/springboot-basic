package com.programmers.springbootbasic.domain.customer.controller;

import com.programmers.springbootbasic.common.response.model.ListResult;
import com.programmers.springbootbasic.common.response.service.ResponseFactory;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.service.BlacklistCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BlacklistCustomerController {
    private final BlacklistCustomerService blacklistCustomerService;

    public ListResult<String> findAllBlacklistCustomer() {
        return ResponseFactory.getListResult(
                blacklistCustomerService.findAllCustomer()
                        .stream()
                        .map(Customer::getInformation)
                        .toList()
        );
    }
}
