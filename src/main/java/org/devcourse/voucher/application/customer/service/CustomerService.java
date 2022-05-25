package org.devcourse.voucher.application.customer.service;

import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, Email email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        return customerRepository.insert(customer);
    }

    public Page<Customer> recallAllCustomer(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
