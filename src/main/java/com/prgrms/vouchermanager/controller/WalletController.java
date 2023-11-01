package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    public Wallet create(UUID customerId, UUID voucherId) {
        return service.create(customerId, voucherId);
    }

    public List<Voucher> findByCustomerId(UUID id) {
        return service.findByCustomerId(id);
    }

    public List<com.prgrms.vouchermanager.domain.customer.Customer> findByVoucherId(UUID id) {
        return service.findByVoucherId(id);
    }

    public int delete(UUID walletId) {
        return service.delete(walletId);
    }
}
