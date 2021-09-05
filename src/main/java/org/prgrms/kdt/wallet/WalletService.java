package org.prgrms.kdt.wallet;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 12:24 오후
 */
@Service
public class WalletService {

    private final WalletJdbcRepository walletJdbcRepository;

    public WalletService(WalletJdbcRepository walletJdbcRepository) {
        this.walletJdbcRepository = walletJdbcRepository;
    }

    @Transactional
    public void addVoucherByCustomer(WalletDto walletDto) {
        Wallet wallet = new Wallet(
                UUID.randomUUID(),
                UUID.fromString(walletDto.getCustomerId()),
                UUID.fromString(walletDto.getVoucherId()));
        walletJdbcRepository.insert(wallet);
    }
}
