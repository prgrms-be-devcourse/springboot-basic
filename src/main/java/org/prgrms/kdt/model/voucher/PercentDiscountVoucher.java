package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.util.ValidatorUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final double MIN_PERCENT = 0.0;
    private static final double MAX_PERCENT = 100.0;

    public PercentDiscountVoucher(UUID voucherId, String discountAmount, VoucherType voucherType, UUID ownedCustomerId, LocalDateTime createdAt) {
        super(voucherId, discountAmount, voucherType, ownedCustomerId, createdAt);
    }

    public PercentDiscountVoucher(UUID voucherId, String discountAmount, VoucherType voucherType, LocalDateTime createdAt) {
        super(voucherId, discountAmount, voucherType, null, createdAt);
    }

    @Override
    protected void validate(String discountAmount) {
        if (ValidatorUtil.isNumeric(discountAmount)) {
            Double discountValue = Double.parseDouble(discountAmount);

            if (discountValue <= MIN_PERCENT || discountValue > MAX_PERCENT) {
                throw new IllegalArgumentException("할인율은 0보다 크고 100과 같거나 작아야 합니다.");
            }
        } else {
            throw new IllegalArgumentException("할인율이 숫자가 아닙니다.");
        }
    }
}
