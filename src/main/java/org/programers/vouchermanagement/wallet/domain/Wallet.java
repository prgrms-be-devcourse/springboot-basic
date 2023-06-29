package org.programers.vouchermanagement.wallet.domain;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.voucher.domain.Voucher;

import java.util.UUID;

public class Wallet {

    private UUID id;
    private Voucher voucher;
    private Member member;

    public Wallet(Voucher voucher, Member member) {
        this(UUID.randomUUID(), voucher, member);
    }

    public Wallet(UUID id, Voucher voucher, Member member) {
        this.id = id;
        this.voucher = voucher;
        this.member = member;
    }

    public UUID getId() {
        return id;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public Member getMember() {
        return member;
    }
}
