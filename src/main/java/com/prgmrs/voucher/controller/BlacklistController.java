package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.service.BlacklistService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class BlacklistController {
    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public Map<UUID, String> findAll() {
        return blacklistService.findAll();
    }
}
