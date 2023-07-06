package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public void save(CustomerCreateRequest customerCreateRequest) {
        customerService.save(customerCreateRequest);
    }

    public void update(CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(customerUpdateRequest);
    }

    public CustomerResponse findById(UUID customerId) {
        return customerService.findById(customerId);
    }

    public CustomerListResponse findAll() {
        return customerService.findAll();
    }

    public CustomerListResponse getBlackList() {
        return customerService.getBlackList();
    }

    public void deleteById(UUID customerId) {
        customerService.deleteById(customerId);
    }

    public void deleteAll() {
        customerService.deleteAll();
    }
}
