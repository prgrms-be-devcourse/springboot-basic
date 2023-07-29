package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.wallet.domain.QueryWallet;
import org.prgrms.kdt.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletQueryRepository {
    List<QueryWallet> findWithMemeberAndVoucherByMemberId(UUID memberId);

    List<QueryWallet> findWithMemeberAndVoucherByVoucherId(UUID voucherId);

    List<QueryWallet> findWithMemeberAndVoucherAll();
}
