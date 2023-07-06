package kr.co.springbootweeklymission.voucher.domain.entity;

import kr.co.springbootweeklymission.common.error.exception.WrongVoucherPolicyException;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
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

    @Builder
    private Voucher(UUID voucherId,
                    int amount,
                    VoucherPolicy voucherPolicy) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherPolicy = voucherPolicy;
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

    public void updateVoucherInformation(VoucherReqDTO.UPDATE update) {
        this.amount = update.getAmount();
        this.voucherPolicy = update.getVoucherPolicy();
    }
}
