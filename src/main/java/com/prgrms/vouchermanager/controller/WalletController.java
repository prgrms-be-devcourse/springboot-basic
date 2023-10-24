package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.domain.Wallet;
import com.prgrms.vouchermanager.service.WalletService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<Customer> findByVoucherId(UUID id) {
        return service.findByVoucherId(id);
    }

    public void delete(UUID customerId, UUID voucherId) {
        service.delete(customerId, voucherId);
    }
}
