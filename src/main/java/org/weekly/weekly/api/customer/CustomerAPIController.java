package org.weekly.weekly.api.customer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.response.CustomerResponse;
import org.weekly.weekly.customer.service.CustomerService;

@RestController
@RequestMapping("/api/v1")
public class CustomerAPIController {
    private final CustomerService customerService;

    public CustomerAPIController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public CustomerResponse create(@RequestBody CustomerCreationRequest creationRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(creationRequest);
        return customerResponse;
    }
}
