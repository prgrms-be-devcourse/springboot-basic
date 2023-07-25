package org.programmers.VoucherManagement.wallet.domain;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.UUID;

public class Wallet {
    private UUID walletId;
    private Voucher voucher;
    private Member member;

    public Wallet(UUID walletId, Voucher voucher, Member member) {
        this.walletId = walletId;
        this.voucher = voucher;
        this.member = member;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public Member getMember() {
        return member;
    }
}
