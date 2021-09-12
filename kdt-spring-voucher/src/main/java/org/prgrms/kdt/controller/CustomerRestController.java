package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.CustomerDto;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public List<CustomerEntity> restFindCustomers() {
        List<CustomerEntity> allCustomers = customerService.getAllCustomers();
        return allCustomers;
    }

    @PostMapping("/{customerId}")
    public CustomerEntity saveCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer) {
        logger.info("Got customer save request {}", customer);
        return CustomerDto.to(customer);
    }

    @DeleteMapping("/{customerId}")
    public void removeCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerEntity updateCustomer(@PathVariable("customerId") UUID customerId,
                                         @RequestBody String email,
                                         @RequestBody String name){
        var customerDto = CustomerDto.builder()
                .customerId(customerId)
                .email(email)
                .name(name)
                .build();
        var updateCustomer = customerService.updateCustomer(customerDto);
        return updateCustomer.get();
    }

    @GetMapping("/{customerId}")
    public CustomerEntity findCustomer(@PathVariable("customerId") UUID customerId){
        var customer = customerService.getCustomer(customerId);
        return customer.get();
    }


}
