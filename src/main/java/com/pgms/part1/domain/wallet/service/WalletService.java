package com.pgms.part1.domain.wallet.service;

import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.domain.wallet.repository.WalletRepository;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final KeyGenerator keyGenerator;

    public WalletService(WalletRepository walletRepository, KeyGenerator keyGenerator) {
        this.walletRepository = walletRepository;
        this.keyGenerator = keyGenerator;
    }

    public void addWallet(WalletCreateRequestDto walletCreateRequestDto) {
        Wallet wallet = new Wallet(keyGenerator.getKey(), walletCreateRequestDto.voucherId(), walletCreateRequestDto.userId());
        walletRepository.addWallet(wallet);
    }

    public void deleteWallet(Long walletId) {
        walletRepository.deleteWallet(walletId);
    }

    public List<Wallet> listWalletsByCustomer(Long customerId) {
        return walletRepository.findWalletByCustomerId(customerId);
    }

    public List<Wallet> listWalletsByVoucher(Long voucherId) {
        return walletRepository.findWalletByVoucherId(voucherId);
    }
}
