package kr.co.springbootweeklymission.domain.voucher.entity;

import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.domain.voucher.exception.WrongVoucherPolicyException;
import kr.co.springbootweeklymission.global.error.ResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Voucher {

    private UUID voucherId;
    private int amount;
    private VoucherPolicy voucherPolicy;

    @Builder
    private Voucher(int amount,
                    VoucherPolicy voucherPolicy) {
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
        this.voucherPolicy = voucherPolicy;
    }

    public int discount(int price) {
        if (price < this.amount) {
            throw new WrongVoucherPolicyException(ResponseStatus.FAIL_WRONG_DISCOUNT);
        }

        return this.voucherPolicy
                .getDiscount()
                .apply(price, this.amount);
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
