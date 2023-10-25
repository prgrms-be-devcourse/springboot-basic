package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerCreateResponse;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateResponse;
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
}
