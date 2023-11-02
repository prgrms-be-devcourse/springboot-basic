package org.prgrms.kdtspringdemo.wallet.service;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherService voucherService;

    public WalletService(WalletRepository walletRepository, VoucherService voucherService) {
        this.walletRepository = walletRepository;
        this.voucherService = voucherService;
    }

    public Wallet create(UUID customerId) {
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId);
        return walletRepository.insert(wallet);
    }

    public Optional<Wallet> findById(UUID walletId){
        return walletRepository.findById(walletId);
    }

    public List<Voucher> findVouchersById(UUID customerId) {
        return walletRepository.findVouchersByCustomerId(customerId);
    }

    public void addVoucherByCustomerId(UUID walletId, UUID customerId, UUID voucherId) {
        walletRepository.addVoucherByCustomerId(walletId, customerId, voucherId);
    }

    public void deleteVoucherByVoucherId(UUID customerId, UUID voucherId) {
        walletRepository.deleteVoucherByVoucherId(customerId, voucherId);
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public void deleteById(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public void deleteAll() {
        walletRepository.deleteAll();
    }
}
