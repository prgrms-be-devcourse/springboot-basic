package org.prgrms.voucherapp.engine.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@AllArgsConstructor
@Getter
public class Wallet {
    private UUID walletId;
    private UUID customerId;
    private UUID voucherId;
}
