package kr.co.springbootweeklymission.vouchermember.domain.entity;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherMember {
    private UUID voucherMemberId;
    private UUID voucherId;
    private UUID memberId;

    @Builder
    private VoucherMember(UUID voucherMemberId,
                          UUID voucherId,
                          UUID memberId) {
        this.voucherMemberId = voucherMemberId;
        this.voucherId = voucherId;
        this.memberId = memberId;
    }
}
