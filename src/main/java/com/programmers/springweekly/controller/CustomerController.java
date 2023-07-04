package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.dto.CustomerCreateDto;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import com.programmers.springweekly.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public void save(CustomerCreateDto customerCreateDto) {
        customerService.save(customerCreateDto);
    }

    public void update(CustomerUpdateDto customerUpdateDto) {
        customerService.update(customerUpdateDto);
    }

    public Optional<Customer> findById(UUID customerId) {
        return customerService.findById(customerId);
    }

    public List<Customer> findAll() {
        return customerService.findAll();
    }

    public List<Customer> getBlackList() {
        return customerService.getBlackList();
    }

    public void deleteById(UUID customerId) {
        customerService.deleteById(customerId);
    }

    public void deleteAll() {
        customerService.deleteAll();
    }
}
