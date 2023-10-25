package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerCreateResponse;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateResponse;
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

    public CustomerCreateResponse save(CustomerCreateRequest customerCreateRequest) {
        return new CustomerCreateResponse(this.customerService.save(customerCreateRequest));
    }

    public CustomerUpdateResponse updateBlacklistStatus(UUID customerId, boolean isBlacklisted) {
        return new CustomerUpdateResponse(this.customerService.updateBlacklistStatus(customerId, isBlacklisted));
    }
}
