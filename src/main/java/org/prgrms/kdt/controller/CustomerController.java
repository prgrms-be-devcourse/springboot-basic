package org.prgrms.kdt.controller;

import org.prgrms.kdt.controller.request.CreateCustomerRequest;
import org.prgrms.kdt.controller.response.CustomerResponse;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.dto.CreateCustomerDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public boolean createCustomer(CreateCustomerRequest createCustomerRequest) {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(createCustomerRequest.email());
        if (customerService.hasDuplicatedCustomer(createCustomerDto.email())) return false;
        else return customerService.createCustomer(createCustomerDto);
    }

    public CustomerResponse getCustomerByEmail(String email) {
        Optional<Customer> returnedCustomer = customerService.getCustomerByEmail(email);
        if (returnedCustomer.isEmpty()) {
            return new CustomerResponse(null);
        }
        return new CustomerResponse(returnedCustomer.get());
    }
}
