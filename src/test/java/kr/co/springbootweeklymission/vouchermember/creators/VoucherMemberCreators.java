package kr.co.springbootweeklymission.vouchermember.creators;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;

import java.util.UUID;

public class VoucherMemberCreators {
    private VoucherMemberCreators() {
    }

    public static VoucherMember createVoucherMember(Voucher voucher,
                                                    Member member) {
        return VoucherMember.builder()
                .voucherMemberId(UUID.randomUUID())
                .voucher(voucher)
                .member(member)
                .build();
    }
}
