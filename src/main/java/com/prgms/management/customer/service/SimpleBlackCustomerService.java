package com.prgms.management.customer.service;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.repository.BlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleBlackCustomerService implements BlackCustomerService {
    private final BlackCustomerRepository blackCustomerRepository;

    public SimpleBlackCustomerService(BlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return blackCustomerRepository.findAll();
    }
}
