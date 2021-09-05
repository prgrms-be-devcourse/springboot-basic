package org.prgrms.kdt.wallet;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:48 오전
 */
public record Wallet(
        UUID voucherWalletId,
        UUID customerId,
        UUID voucherId
) {

    public Wallet {
        Objects.requireNonNull(voucherWalletId);
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(voucherId);
    }
}
