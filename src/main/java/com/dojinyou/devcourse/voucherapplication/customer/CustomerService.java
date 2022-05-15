package com.dojinyou.devcourse.voucherapplication.customer;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer create(Customer customer) {
        return customerRepository.create(customer);
    };
    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void deleteById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        customerRepository.deleteById(id);
    }
}
