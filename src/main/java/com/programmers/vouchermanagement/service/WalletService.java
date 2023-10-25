package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.CreateWalletRequestDto;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createWallet(CreateWalletRequestDto request) {
        walletRepository.save(new Wallet(request.getCustomerId(), request.getVoucherId()));
    }

    public List<Wallet> getWallets(GetWalletsRequestDto request) {
        return walletRepository.findAll(request);
    }

    public void deleteWallet(int id) {
        walletRepository.deleteById(id);
    }
}
