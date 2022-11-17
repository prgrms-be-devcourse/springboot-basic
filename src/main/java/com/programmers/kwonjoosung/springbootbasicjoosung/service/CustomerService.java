package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.FileCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final FileCustomerRepository fileCustomerRepository;

    public CustomerService(FileCustomerRepository fileCustomerRepository) {
        this.fileCustomerRepository = fileCustomerRepository;
    }

    public List<Customer> getBlackList() {
        return fileCustomerRepository.findAll();
    }
}
