package org.prgrms.vouchermanager.repository.wallet;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcWalletRepository implements WalletRepository {

    @Override
    public Wallet save(WalletRequestDto walletRequestDto) {
        return null;
    }

    @Override
    public Wallet findByEmail(WalletRequestDto walletRequestDto) {
        return null;
    }

    @Override
    public void deleteByEmail(WalletRequestDto walletRequestDto) {

    }

    @Override
    public Wallet findByVoucher(WalletRequestDto walletRequestDto) {
        return null;
    }
}
