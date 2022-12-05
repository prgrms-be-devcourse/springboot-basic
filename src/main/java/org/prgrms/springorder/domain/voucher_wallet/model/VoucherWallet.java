package org.prgrms.springorder.domain.voucher_wallet.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherWallet {

    private final UUID walletId;

    private final UUID customerId;

    private final UUID voucherId;

    private final LocalDateTime createdAt;

    public static VoucherWallet create(UUID walletId, UUID customerId, UUID voucherId) {
        return new VoucherWallet(walletId, customerId, voucherId, LocalDateTime.now());
    }
}
