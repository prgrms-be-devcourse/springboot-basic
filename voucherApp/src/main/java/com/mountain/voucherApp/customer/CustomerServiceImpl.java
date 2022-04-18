package com.mountain.voucherApp.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Customer customer) {
        customerRepository.update(customer);
        return customer;
    }

    @Override
    public List<Customer> findByVoucherId(UUID voucherId) {
        return customerRepository.findByVoucherId(voucherId);
    }

    @Override
    public List<Customer> findByVoucherIdNotNull() {
        return customerRepository.findByVoucherIdNotNull();
    }

    @Override
    public void removeByCustomerId(UUID customerId) {
        customerRepository.removeByCustomerId(customerId);
    }
}
