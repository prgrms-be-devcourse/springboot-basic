package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.devcourse.voucher.customer.repository.BlacklistRepository;
import org.devcourse.voucher.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(String name, Email email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        return customerRepository.insert(customer);
    }

    @Override
    public List<Customer> recallAllCustomer() {
        return customerRepository.findAll();
    }
}
