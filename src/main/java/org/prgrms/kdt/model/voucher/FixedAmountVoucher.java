package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.util.ValidatorUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, String discountAmount, VoucherType voucherType, UUID ownedCustomerId, LocalDateTime createdAt) {
        super(voucherId, discountAmount, voucherType, ownedCustomerId, createdAt);
    }

    public FixedAmountVoucher(UUID voucherId, String discountAmount, VoucherType voucherType, LocalDateTime createdAt) {
        super(voucherId, discountAmount, voucherType, null, createdAt);
    }

    @Override
    protected void validate(String discountAmount) {
        if (ValidatorUtil.isNumeric(discountAmount)) {
            double discountValue = Double.parseDouble(discountAmount);

            if (discountValue != Math.floor(discountValue)) {
                throw new IllegalArgumentException("할인 금액이 정수가 아닙니다.");
            } else if (discountValue < 0) {
                throw new IllegalArgumentException("할인 금액이 음수입니다.");
            }
        } else {
            throw new IllegalArgumentException("할인금액이 숫자가 아닙니다.");
        }
    }
}
