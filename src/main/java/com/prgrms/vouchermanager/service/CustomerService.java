package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.exception.NotCorrectId;
import com.prgrms.vouchermanager.repository.customer.BlacklistRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
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

    public List<Customer> findBlacklist() {
        return blacklistRepository.findBlacklist();
    }

    public Customer create(String name, int year) {
        Customer customer = new Customer(name, year);

        customerRepository.create(customer);
        return customer;
    }

    public List<Customer> findAll() {
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

    public int delete(UUID id) {
        int update = customerRepository.delete(id);

        if(update == 0) throw new NotCorrectId();
        return update;
    }
}
