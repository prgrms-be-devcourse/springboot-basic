package org.prgrms.kdt.common.util;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherUtil {
    public static Voucher createVoucherByType(UUID voucherId, long value, LocalDateTime createdAt, VoucherType voucherType){
        if(voucherType == VoucherType.FIXED)
            return new FixedAmountVoucher(voucherId, value, createdAt);
        else
            return new PercentDiscountVoucher(voucherId, value, createdAt);
    }
}
