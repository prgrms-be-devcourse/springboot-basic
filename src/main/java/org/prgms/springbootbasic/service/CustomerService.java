package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findBlackAll(){
        return customerRepository.findBlackAll();
    }
}
