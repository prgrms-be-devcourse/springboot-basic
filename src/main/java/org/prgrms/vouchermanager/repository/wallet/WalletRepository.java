package org.prgrms.vouchermanager.repository.wallet;

import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;

import java.util.Optional;

public interface WalletRepository {
    WalletRequestDto save(WalletRequestDto walletRequestDto);
    Optional<Wallet> findByEmail(String email);
    Optional<Wallet> deleteByEmail(String email);
    Optional<Wallet> findByVoucher(Voucher voucher);
}
