package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.repository.VoucherRepository;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public void createWallet(Wallet wallet) {
        walletRepository.insert(wallet);
    }

    public void updateWallet(Wallet wallet) {
        walletRepository.update(wallet);
    }

    public void deleteWalletById(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public List<Wallet> findWalletsByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId);
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        List<Wallet> wallets = findWalletsByCustomerId(customerId);
        return wallets.stream()
                .map(wallet -> voucherRepository.findById(wallet.voucherId())
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())))
                .toList();
    }

    public List<Wallet> findWalletsByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId);
    }

    public List<Customer> findCustomersByVoucherId(UUID voucherId) {
        List<Wallet> wallets = findWalletsByVoucherId(voucherId);
        return wallets.stream()
                .map(wallet -> customerRepository.findById(wallet.customerId())
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())))
                .toList();
    }

}
