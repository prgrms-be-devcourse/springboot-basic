package org.prgrms.kdt.wallet.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.wallet.repository.WalletRepository;
import org.prgrms.kdt.wallet.model.Wallet;
import org.prgrms.kdt.wallet.model.WalletVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    @Transactional
    public Wallet createWallet(WalletVO walletVO) {
        var wallet = new Wallet(
            UUID.randomUUID(),
            walletVO.customerId(),
            walletVO.voucherId(),
            LocalDateTime.now()
        );
        return walletRepository.insert(wallet);
    }

    @Transactional
    public void deleteWallet(UUID customerId, UUID voucherId) {
        walletRepository.deleteByCustomerVoucher(customerId, voucherId);
    }
}
