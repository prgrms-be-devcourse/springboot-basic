package org.prgrms.kdt.wallet;

import lombok.Getter;
import lombok.ToString;
import org.prgrms.kdt.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
public class Wallet {
    private final UUID walletId;

    private final UUID customerId;

    private List<Voucher> vouchers;

    public Wallet(UUID walletId, UUID customerId){
        this.walletId = walletId;
        this.customerId = customerId;
    }
}
