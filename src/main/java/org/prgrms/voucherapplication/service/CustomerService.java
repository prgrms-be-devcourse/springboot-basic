package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.repository.FileCustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {
    private FileCustomerRepository repository;

    public CustomerService(FileCustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomer() throws IOException {
        return repository.findAll();
    }
}
