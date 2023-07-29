package org.programmers.VoucherManagement.wallet.domain;

import org.programmers.VoucherManagement.global.entity.BaseTimeEntity;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

public class Wallet extends BaseTimeEntity {
    private String walletId;
    private Voucher voucher;
    private Member member;

    public Wallet(String walletId, Voucher voucher, Member member) {
        this.walletId = walletId;
        this.voucher = voucher;
        this.member = member;
    }

    public String getWalletId() {
        return walletId;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public Member getMember() {
        return member;
    }
}
