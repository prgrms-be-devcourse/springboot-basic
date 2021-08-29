package com.programmers.kdtspringorder.customer;

import com.programmers.kdtspringorder.voucher.domain.Voucher;
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

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public Customer insert(Customer customer) {
        return customerRepository.insert(customer);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
