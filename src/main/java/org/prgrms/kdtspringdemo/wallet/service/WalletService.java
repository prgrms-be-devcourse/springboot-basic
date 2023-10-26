package org.prgrms.kdtspringdemo.wallet.service;

import org.prgrms.kdtspringdemo.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


}
