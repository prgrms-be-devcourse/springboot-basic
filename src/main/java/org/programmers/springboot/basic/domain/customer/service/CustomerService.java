package org.programmers.springboot.basic.domain.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.config.CustomerConfig;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConfig customerConfig;

    public CustomerService(CustomerRepository customerRepository, CustomerConfig customerConfig) {
        this.customerRepository = customerRepository;
        this.customerConfig = customerConfig;
    }

    public List<CustomerResponseDto> findBlackList() {

        List<Customer> customers = this.customerRepository.findBlackList();

        if (customers.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        return customers.stream()
                .map(customerConfig::getCustomerResponseDto)
                .collect(Collectors.toList());
    }
}
