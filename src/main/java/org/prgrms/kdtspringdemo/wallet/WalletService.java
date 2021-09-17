package org.prgrms.kdtspringdemo.wallet;

import org.prgrms.kdtspringdemo.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public void addVoucher(String customerId, UUID voucherId) {
        Wallet wallet = new Wallet(UUID.randomUUID(),
                UUID.fromString(customerId),
                voucherId,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        walletRepository.insert(wallet);
    }

    public void deleteCustomer(String customerId) {
        walletRepository.deleteByCustomerId(customerId);
    }

    public void deleteVoucher(String voucherId) {
        walletRepository.deleteByVoucherId(voucherId);
    }
}
