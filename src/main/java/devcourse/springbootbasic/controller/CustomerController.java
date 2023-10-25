package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerResponse;
import devcourse.springbootbasic.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    public List<CustomerFindResponse> findAllBlacklistedCustomers() {
        return this.customerService.findAllBlacklistedCustomers();
    }

    public CustomerResponse save(CustomerCreateRequest customerCreateRequest) {
        return new CustomerResponse(this.customerService.save(customerCreateRequest));
    }

    public CustomerResponse updateBlacklistStatus(UUID customerId, boolean isBlacklisted) {
        return new CustomerResponse(this.customerService.updateBlacklistStatus(customerId, isBlacklisted));
    }
}
