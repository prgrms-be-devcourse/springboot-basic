package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet allocateVoucherToCustomer(UUID customerId, UUID voucherId) {
        return walletRepository.allocateVoucherToCustomer(customerId, voucherId);
    }
}
