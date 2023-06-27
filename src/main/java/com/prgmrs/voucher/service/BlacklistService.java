package com.prgmrs.voucher.service;

import com.prgmrs.voucher.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class BlacklistService {
    private BlacklistRepository blacklistRepository;

    @Autowired
    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }
    public Map<UUID, String> findAll() {
        return blacklistRepository.findAll();
    }

}
