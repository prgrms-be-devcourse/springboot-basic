package com.voucher.vouchermanagement.service;

import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {

    private final BlacklistRepository blackListRepository;

    public BlacklistService(BlacklistRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public List<Customer> findAll() {
        return this.blacklistRepository.findAll();
    }
}
