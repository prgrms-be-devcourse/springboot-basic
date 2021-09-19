package org.prgrms.dev.wallet.service;

import org.prgrms.dev.customer.domain.Customer;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.wallet.domain.Wallet;
import org.prgrms.dev.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;


    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Voucher> getAllocatedVoucher(UUID customerId) {
        return walletRepository.findVoucherByCustomerId(customerId);
    }

    public List<Voucher> getNotAllocatedVoucher(UUID customerId) {
        return walletRepository.findNoVoucherByCustomerId(customerId);
    }

    public List<Customer> getCustomersWithSpecificVouchers(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId);
    }

    public int allocateVoucher(Wallet wallet) {
        return walletRepository.insert(wallet);
    }

    public void deleteWallet(UUID walletId) {
        walletRepository.deleteById(walletId);
    }
}
