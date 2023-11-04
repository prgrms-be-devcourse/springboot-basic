package org.prgrms.vouchermanager.repository.wallet;

import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.dto.WalletRequest;

import java.util.Optional;

public interface WalletRepository {
    WalletRequest save(WalletRequest walletRequest);
    Optional<Wallet> findByEmail(String email);
    Optional<Wallet> deleteByEmail(String email);
    Optional<Wallet> findByVoucher(Voucher voucher);
}
