package org.prgms.order.customer.service;

import org.prgms.order.customer.entity.Customer;
import org.prgms.order.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAllBlackList() {
        return customerRepository.findBlackList();
    }
}

