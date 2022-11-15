package org.programmers.springbootbasic.service;

import org.programmers.springbootbasic.domain.Customer;
import org.programmers.springbootbasic.repository.CustomerBlackListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    CustomerBlackListRepository customerBlackListRepository;

    public CustomerService(CustomerBlackListRepository customerBlackListRepository) {
        this.customerBlackListRepository = customerBlackListRepository;
    }

    public List<Customer> collectBlacklists() {
        return customerBlackListRepository.findAll();
    }
}
