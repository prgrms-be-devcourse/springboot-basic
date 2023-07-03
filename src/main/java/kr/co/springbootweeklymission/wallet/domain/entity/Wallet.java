package kr.co.springbootweeklymission.wallet.domain.entity;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Wallet {
    private UUID walletId;
    private Voucher voucher;
    private Member member;

    @Builder
    private Wallet(UUID walletId,
                   Voucher voucher,
                   Member member) {
        this.walletId = walletId;
        this.voucher = voucher;
        this.member = member;
    }

    public static Wallet toVoucherMember(Voucher voucher,
                                         Member member) {
        return Wallet.builder()
                .walletId(UUID.randomUUID())
                .voucher(voucher)
                .member(member)
                .build();
    }
}
