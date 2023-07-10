package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.BlacklistResponse;
import com.prgmrs.voucher.service.BlacklistService;
import org.springframework.stereotype.Component;

@Component
public class BlacklistController {
    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public BlacklistResponse findAll() {
        return blacklistService.findAll();
    }
}
