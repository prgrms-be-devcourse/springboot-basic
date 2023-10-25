package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateBlacklistRequest;
import devcourse.springbootbasic.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

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

    public CustomerResponse updateBlacklistStatus(CustomerUpdateBlacklistRequest customerUpdateBlacklistRequest) {
        return new CustomerResponse(this.customerService.updateBlacklistStatus(customerUpdateBlacklistRequest));
    }
}
