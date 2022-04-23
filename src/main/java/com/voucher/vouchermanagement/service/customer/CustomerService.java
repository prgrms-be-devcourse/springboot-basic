package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;

import java.util.List;
import java.util.UUID;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void join(CustomerJoinRequest joinRequest) {

    }

    public List<Customer> findAll() {
        return null;
    }

    public Customer findById(UUID id) {
        return null;
    }
}
