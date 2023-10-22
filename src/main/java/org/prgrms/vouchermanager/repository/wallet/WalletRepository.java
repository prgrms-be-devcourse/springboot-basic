package org.prgrms.vouchermanager.repository.wallet;

import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;

public interface WalletRepository {
    Wallet save(WalletRequestDto walletRequestDto);
    Wallet findByEmail(WalletRequestDto walletRequestDto);
    void deleteByEmail(WalletRequestDto walletRequestDto);
    Wallet findByVoucher(WalletRequestDto walletRequestDto);
}
