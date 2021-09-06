package org.prgrms.kdt.wallet;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.voucher.Voucher;

/**
 * Created by yhh1056
 * Date: 2021/09/05 Time: 3:03 오후
 */
public interface WalletRepository {

    int insert(Wallet wallet);

    List<Voucher> findByCustomerId(UUID customerId);

    int deleteAll();

    int deleteBy(UUID customerId, UUID voucherId);
}
