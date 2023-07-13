package org.prgrms.kdt.wallet.domain;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.UUID;

public class JoinedWallet {
    private final UUID walletId;
    private final Member member;
    private final Voucher voucher;

    public JoinedWallet(UUID walletId, Member member, Voucher voucher) {
        this.walletId = walletId;
        this.member = member;
        this.voucher = voucher;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public Member getMember() {
        return member;
    }

    public Voucher getVoucher() {
        return voucher;
    }
}
