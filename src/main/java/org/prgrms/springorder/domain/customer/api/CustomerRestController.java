package org.prgrms.springorder.domain.customer.api;

import java.util.List;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.customer.api.response.CustomerResponse;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/customers")
@RestController
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(
        CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customerResponses = customerService.findAllCustomers()
            .stream()
            .map(customer -> new CustomerResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreatedAt(),
                customer.getCustomerStatus()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(customerResponses);
    }


}
