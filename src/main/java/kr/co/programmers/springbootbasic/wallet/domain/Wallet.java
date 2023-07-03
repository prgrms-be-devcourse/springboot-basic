package kr.co.programmers.springbootbasic.wallet.domain;

import java.util.UUID;

public class Wallet {
    private final UUID id;
    private final UUID voucher_id;

    public Wallet(UUID id, UUID voucher_id) {
        this.id = id;
        this.voucher_id = voucher_id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getVoucher_id() {
        return voucher_id;
    }
}
