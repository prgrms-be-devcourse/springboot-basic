package org.prgrms.kdtspringdemo.wallet;

import org.prgrms.kdtspringdemo.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //todo: customer:addWallet 시 wallet을 선택해서 넣는 기능 구현하기
    @Transactional
    public Wallet addWallet(UUID customerId) {
        Wallet wallet = new Wallet(UUID.randomUUID(),
                customerId,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        walletRepository.insert(wallet);

        return wallet;
    }
}
