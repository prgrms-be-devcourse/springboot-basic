package org.programers.vouchermanagement.wallet.dto.response;

import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.wallet.domain.Wallet;

import java.util.UUID;

public class WalletResponse {

    private final UUID id;
    private final VoucherResponse voucher;
    private final MemberResponse member;

    public WalletResponse(Wallet wallet) {
        this(wallet.getId(), new VoucherResponse(wallet.getVoucher()),
                new MemberResponse(wallet.getMember()));
    }

    public WalletResponse(UUID id, VoucherResponse voucher, MemberResponse member) {
        this.id = id;
        this.voucher = voucher;
        this.member = member;
    }

    public UUID getId() {
        return id;
    }

    public VoucherResponse getVoucher() {
        return voucher;
    }

    public MemberResponse getMember() {
        return member;
    }
}
