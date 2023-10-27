package org.prgrms.kdtspringdemo.wallet.service;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        UUID walletId = UUID.randomUUID();
        List<UUID> vouchers = new ArrayList<>();
        Wallet wallet = new Wallet(walletId, customerId, vouchers);
        return walletRepository.insert(wallet);
    }

    public List<Voucher> findVouchersById(UUID customerId) {
        List<UUID> vouchers = walletRepository.findVouchersByCustomerId(customerId).get();

        List<Voucher> voucherList = new ArrayList<>();
        vouchers.stream().forEach(uuid -> voucherList.add(voucherService.findById(uuid)));
        return voucherList;
    }

    public List<UUID> addVoucherByCustomerId(UUID customerId, UUID voucherId) {
        return walletRepository.addVoucherByCustomerId(customerId, voucherId);
    }

    public void deleteVoucherByVoucherId(UUID customerId, UUID voucherId) {
        walletRepository.deleteVoucherByVoucherId(customerId, voucherId);
    }

    public void deleteAll() {
        walletRepository.deleteAll();
    }
}
