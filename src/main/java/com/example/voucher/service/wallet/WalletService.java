package com.example.voucher.service.wallet;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.voucher.domain.wallet.Wallet;
import com.example.voucher.repository.wallet.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletDTO createWallet(UUID customerId, UUID voucherId) {
        Wallet createdWallet = new Wallet(customerId, voucherId);

        Wallet savedWallet = walletRepository.save(createdWallet);

        return toDTO(savedWallet);
    }

    private WalletDTO toDTO(Wallet wallet) {
        UUID walletId = wallet.getWalletId();
        UUID customerId = wallet.getCustomerId();
        UUID voucherId = wallet.getVoucherId();

        return new WalletDTO(walletId, customerId, voucherId);
    }

}
