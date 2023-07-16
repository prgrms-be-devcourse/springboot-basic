package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.service.CustomerService;
import com.programmers.springweekly.util.validator.CustomerValidator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public CustomerResponse save(CustomerCreateRequest customerCreateRequest) {
        CustomerValidator.validateCustomer(
                customerCreateRequest.getCustomerName(),
                customerCreateRequest.getCustomerEmail(),
                customerCreateRequest.getCustomerType()
        );

        return customerService.save(customerCreateRequest);
    }

    public void update(CustomerUpdateRequest customerUpdateRequest) {
        CustomerValidator.validateCustomer(
                customerUpdateRequest.getCustomerName(),
                customerUpdateRequest.getCustomerEmail(),
                customerUpdateRequest.getCustomerType()
        );

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

    public int deleteById(UUID customerId) {
        return customerService.deleteById(customerId);
    }

    public void deleteAll() {
        customerService.deleteAll();
    }

    public boolean existById(UUID voucherId) {
        return customerService.existById(voucherId);
    }

}
