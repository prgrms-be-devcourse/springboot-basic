package com.prgms.management.customer.service;

import com.prgms.management.customer.entity.Customer;
import com.prgms.management.customer.exception.CustomerException;
import com.prgms.management.customer.repository.BlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleCustomerService implements CustomerService {
    private final BlackCustomerRepository blackCustomerRepository;

    public SimpleCustomerService(BlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerException {
        return blackCustomerRepository.findAll();
    }
}
