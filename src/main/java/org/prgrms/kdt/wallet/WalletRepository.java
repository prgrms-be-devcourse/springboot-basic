package org.prgrms.kdt.wallet;

import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/09/05 Time: 3:03 오후
 */
public interface WalletRepository {

    int insert(Wallet wallet);

    int deleteAll();

    int deleteBy(UUID customerId, UUID voucherId);
}
