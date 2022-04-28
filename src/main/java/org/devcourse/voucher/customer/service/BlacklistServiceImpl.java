package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.Customer;
import org.devcourse.voucher.customer.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistServiceImpl implements BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistServiceImpl(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public List<Customer> recallAllBlacklist() {
        return blacklistRepository.findAll();
    }
}
