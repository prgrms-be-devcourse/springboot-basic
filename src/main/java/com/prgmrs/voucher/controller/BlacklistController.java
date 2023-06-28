package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class BlacklistController {
    private BlacklistService blacklistService;
    @Autowired
    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public Map<UUID, String> findAll() {
        return blacklistService.findAll();
    }
}
