package kr.co.springbootweeklymission.voucher.domain.entity;

import kr.co.springbootweeklymission.infrastructure.error.exception.WrongVoucherPolicyException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Voucher {
    private UUID voucherId;
    private int amount;
    private VoucherPolicy voucherPolicy;
    private UUID memberId;

    @Builder
    private Voucher(UUID voucherId,
                    int amount,
                    VoucherPolicy voucherPolicy, UUID memberId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherPolicy = voucherPolicy;
        this.memberId = memberId;
    }

    public static Voucher toVoucher(VoucherReqDTO.CREATE create) {
        return Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(create.getAmount())
                .voucherPolicy(create.getVoucherPolicy())
                .build();
    }

    public int discount(int price) {
        if (price < this.amount) {
            throw new WrongVoucherPolicyException(ResponseStatus.FAIL_WRONG_DISCOUNT);
        }

        return this.voucherPolicy
                .getDiscount()
                .apply(price, this.amount);
    }

    public boolean isMember() {
        return this.memberId != null;
    }

    public void assignVoucher(Member member) {
        this.memberId = member.getMemberId();
    }

    public void unassignedVoucher() {
        this.memberId = null;
    }

    public void updateVoucherInformation(VoucherReqDTO.UPDATE update) {
        this.amount = update.getAmount();
        this.voucherPolicy = update.getVoucherPolicy();
    }
}
