package org.prgrms.springbootbasic.dto;

import java.io.Serializable;
import java.util.UUID;
import org.prgrms.springbootbasic.VoucherType;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;

public class VoucherDTO implements Serializable {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final int amount;
    private final int percent;

    public VoucherDTO(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();

        if (voucher instanceof FixedAmountVoucher) {
            this.voucherType = VoucherType.FIXED;
            this.amount = ((FixedAmountVoucher) voucher).getAmount();
            this.percent = 0;
        } else {
            this.voucherType = VoucherType.PERCENT;
            this.percent = ((PercentDiscountVoucher) voucher).getPercent();
            this.amount = 0;
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getAmount() {
        return amount;
    }

    public int getPercent() {
        return percent;
    }
}
