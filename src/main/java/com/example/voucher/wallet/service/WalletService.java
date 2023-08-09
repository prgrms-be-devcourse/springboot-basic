package com.example.voucher.wallet.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.voucher.wallet.model.Wallet;
import com.example.voucher.wallet.repository.WalletRepository;
import com.example.voucher.wallet.service.dto.WalletDTO;

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

    public List<WalletDTO> getByCustomer(UUID customerId) {
        return walletRepository.findByCustomerId(customerId)
            .stream()
            .map(v -> toDTO(v))
            .toList();
    }

    public List<WalletDTO> getByVoucher(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId)
            .stream()
            .map(v -> toDTO(v))
            .toList();
    }

    public void deleteWallet(UUID customerId, UUID voucherId) {
        walletRepository.deleteById(customerId, voucherId);
    }

    private WalletDTO toDTO(Wallet wallet) {
        UUID walletId = wallet.getWalletId();
        UUID customerId = wallet.getCustomerId();
        UUID voucherId = wallet.getVoucherId();

        return new WalletDTO(walletId, customerId, voucherId);
    }

}
