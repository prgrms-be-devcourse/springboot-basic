package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.devcourse.voucher.customer.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(UUID customerId, String name, Email email) {
        return null;
    }

    @Override
    public List<Customer> recallAllCustomer() {
        return null;
    }
}
