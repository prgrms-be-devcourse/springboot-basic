package org.programmers.weekly.mission.domain.wallet;

import org.programmers.weekly.mission.domain.customer.model.Customer;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWallet(UUID customerId, UUID voucherId) {
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId, voucherId);
        walletRepository.insert(wallet);
    }

    public List<Voucher> getVoucherByCustomerId(UUID customerId) {
        return walletRepository.findVoucherByCustomerId(customerId);
    }

    public List<Customer> getCustomerByVoucherId(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId);
    }

    public void deleteVoucherCustomerHave(UUID customerId) {
        walletRepository.deleteVouchersByCustomerId(customerId);
    }
}
