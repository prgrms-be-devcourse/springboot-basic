package com.prgrms.w3springboot.customer.service;

import com.prgrms.w3springboot.customer.BlacklistCustomer;
import com.prgrms.w3springboot.customer.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<BlacklistCustomer> getBlacklist() {
        return blacklistRepository.findAll();
    }
}
