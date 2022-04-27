package com.prgrms.voucher_manager.wallet.service;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.service.VoucherService;
import com.prgrms.voucher_manager.wallet.Wallet;
import com.prgrms.voucher_manager.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WalletService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final WalletRepository walletRepository;


    public WalletService(WalletRepository walletRepository, VoucherService voucherService, CustomerService customerService) {
        this.walletRepository = walletRepository;
    }

    public List<UUID> getWalletIdByVoucherType(List<Voucher> vouchers) {
        List<UUID> walletIds = new ArrayList<>();
        vouchers.forEach(v -> {
            List<Wallet> wallets = walletRepository.findByVoucherId(v.getVoucherId());
            wallets.forEach(w -> walletIds.add(w.getCustomerId()));
        });
        return walletIds;
    }

    public void deleteWallet(UUID customerId, UUID voucherId) {
        Wallet deleteWallet = new Wallet(customerId, voucherId);
        walletRepository.deleteByVoucherId(deleteWallet);
    }


    public void createWallet(UUID customerId, UUID voucherId) {
        Wallet createWallet = new Wallet(customerId, voucherId);
        walletRepository.insert(createWallet);
    }

    public List<Wallet> findAllWallet(UUID customerId) {
        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);
        return wallets;
    }

//    private void deleteVoucher(UUID customerId, UUID voucherId) {
//        Wallet deleteWallet = new Wallet(customerId, voucherId);
//        walletRepository.deleteByVoucherId(deleteWallet);
//    }

}
