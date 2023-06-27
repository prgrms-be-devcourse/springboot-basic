package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final BlacklistRepository blacklistRepository;

    public CustomerService(BlacklistRepository customerRepository) {
        this.blacklistRepository = customerRepository;
    }

    public List<Customer> findBlacklistCustomers() {
        return blacklistRepository.findAll();
    }
}
