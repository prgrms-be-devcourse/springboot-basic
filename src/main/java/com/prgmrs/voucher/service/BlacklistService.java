package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.response.BlacklistResponse;
import com.prgmrs.voucher.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public BlacklistResponse findAll() {
        return new BlacklistResponse(blacklistRepository.findAll());
    }
}
