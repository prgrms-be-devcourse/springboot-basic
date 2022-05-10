package com.waterfogsw.voucher.customer.service;

import com.waterfogsw.voucher.customer.model.Customer;
import com.waterfogsw.voucher.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDefaultService implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDefaultService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException();
        }
        customerRepository.insert(customer);
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        return customerRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        final var foundCustomer = customerRepository.findById(id);
        customerRepository.deleteById(foundCustomer.getId());
    }
}
