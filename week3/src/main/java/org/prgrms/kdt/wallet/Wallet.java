package org.prgrms.kdt.wallet;

import lombok.Getter;
import org.prgrms.kdt.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

@Getter
public class Wallet {
    final private UUID walletId;

    final private UUID customerId;

    private List<Voucher> vouchers;

    Wallet(UUID walletId, UUID customerId){
        this.walletId = walletId;
        this.customerId = customerId;
    }
}
