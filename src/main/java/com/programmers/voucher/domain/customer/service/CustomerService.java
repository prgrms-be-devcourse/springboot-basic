package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.repository.BlacklistRepository;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final BlacklistRepository blacklistRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(BlacklistRepository customerRepository, CustomerRepository customerRepository1) {
        this.blacklistRepository = customerRepository;
        this.customerRepository = customerRepository1;
    }

    public List<Customer> findBlacklistCustomers() {
        return blacklistRepository.findAll();
    }
}
