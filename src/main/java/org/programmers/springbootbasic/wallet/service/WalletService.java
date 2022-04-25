package org.programmers.springbootbasic.wallet.service;

import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.wallet.domain.Wallet;
import org.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Voucher> getVouchers(UUID customerId) {
        return walletRepository.findVoucherByCustomerId(customerId);
    }

    public List<Customer> getCustomers(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId);
    }

    public Wallet allocateVoucher(Wallet wallet) {
        return walletRepository.insert(wallet);
    }

    public void deleteWallet(UUID walletId) {
        walletRepository.deleteById(walletId);
    }
}
