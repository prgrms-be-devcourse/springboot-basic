package kr.co.springbootweeklymission.vouchermember.domain.entity;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherMember {
    private UUID voucherMemberId;
    private Voucher voucher;
    private Member member;

    @Builder
    private VoucherMember(UUID voucherMemberId,
                          Voucher voucher,
                          Member member) {
        this.voucherMemberId = voucherMemberId;
        this.voucher = voucher;
        this.member = member;
    }

    public static VoucherMember toVoucherMember(Voucher voucher,
                                                Member member) {
        return VoucherMember.builder()
                .voucherMemberId(UUID.randomUUID())
                .voucher(voucher)
                .member(member)
                .build();
    }
}
