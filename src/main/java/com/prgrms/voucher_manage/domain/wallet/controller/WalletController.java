package com.prgrms.voucher_manage.domain.wallet.controller;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;
import com.prgrms.voucher_manage.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller @RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    public void saveWallet(UUID customerId, UUID voucherId){
        walletService.save(new Wallet(customerId, voucherId));
    }

    public List<Voucher> findVouchers(UUID customerId){
        return walletService.findByCustomerId(customerId);
    }

    public List<Customer> findCustomers(UUID voucherId){
        return walletService.findByVoucherId(voucherId);
    }
    public void deleteWallet(UUID customerId, UUID voucherId){
        walletService.delete(new Wallet(customerId, voucherId));
    }
}
