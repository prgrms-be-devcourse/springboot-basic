package kr.co.programmers.springbootbasic.wallet.domain;

import kr.co.programmers.springbootbasic.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public class Wallet {
    private final UUID id;
    private final List<Voucher> vouchers;

    public Wallet(UUID id, List<Voucher> vouchers) {
        this.id = id;
        this.vouchers = vouchers;
    }

    public UUID getId() {
        return id;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }
}
