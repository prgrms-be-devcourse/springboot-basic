package com.voucher.vouchermanagement.service.blacklist;

import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistFileRepository;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistFileRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<Customer> findAll() {
        return this.blacklistRepository.findAll();
    }
}
