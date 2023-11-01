package org.prgrms.vouchermanager.handler.customer;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.customer.CustomerRequest;
import org.prgrms.vouchermanager.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class ApiCustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        List<Customer> customers = customerService.findAll();
        return customers;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody CustomerRequest request){
        return customerService.createCustomer(request);
    }
}
