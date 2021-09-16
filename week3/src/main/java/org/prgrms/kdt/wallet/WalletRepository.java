package org.prgrms.kdt.wallet;

import org.prgrms.kdt.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByWalletId(UUID walletId);

    int insert(UUID walletId, UUID customerId);

    List<Wallet> findAll();

    List<Voucher> findVouchersByWalletList(List<byte[]> wallets);
}
