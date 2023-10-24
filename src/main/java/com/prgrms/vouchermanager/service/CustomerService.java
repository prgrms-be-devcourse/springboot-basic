package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.exception.NotCorrectId;
import com.prgrms.vouchermanager.repository.BlacklistRepository;
import com.prgrms.vouchermanager.repository.CustomerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final BlacklistRepository blacklistRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(BlacklistRepository blacklistRepository, CustomerRepository customerRepository) {
        this.blacklistRepository = blacklistRepository;
        this.customerRepository = customerRepository;
    }

    public List<Customer> blacklist() {
        return blacklistRepository.blacklist();
    }

    public Customer create(String name, int year) {
        Customer customer = new Customer(name, year);

        customerRepository.create(customer);
        return customer;
    }

    public List<Customer> list() {
        return customerRepository.list();
    }

    public Customer updateYearOfBirth(UUID id, int year) {
        try {
            return customerRepository.updateYearOfBirth(id, year);
        } catch (EmptyResultDataAccessException e) {
            throw new NotCorrectId();
        }
    }

    public Customer updateName(UUID id, String name) {
        try {
            return customerRepository.updateName(id, name);
        } catch (EmptyResultDataAccessException e) {
            throw new NotCorrectId();
        }
    }

    public UUID delete(UUID id) {
        try {
            return customerRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotCorrectId();
        }
    }
}
