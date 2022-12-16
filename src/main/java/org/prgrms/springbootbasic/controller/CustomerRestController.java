package org.prgrms.springbootbasic.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.springbootbasic.dto.CustomerInputDto;
import org.prgrms.springbootbasic.dto.CustomerUpdateDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.exception.CustomerNotFoundException;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.util.UUIDUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    @PostMapping("/new")
    public Customer createCustomer(@RequestBody CustomerInputDto customerInputDto) {
        return customerService.createCustomer(customerInputDto);
    }

    @PatchMapping("/{customerId}")
    public Customer editCustomer(@PathVariable String customerId, @RequestBody CustomerUpdateDto customerUpdateDto) {
        validate(customerId);
        customerUpdateDto.setCustomerId(customerId);
        return customerService.editCustomer(customerUpdateDto);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable String customerId) {
        validate(customerId);
        return customerService.getCustomerById(customerId);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> removeCustomer(@PathVariable String customerId) {
        validate(customerId);
        customerService.removeCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<Customer> getCustomerList() {
        return customerService.getCustomerList();
    }

    private void validate(String customerId) {
        if (!UUIDUtil.isUUID(customerId)) {
            throw new CustomerNotFoundException();
        }
    }

}
