package com.prgms.management.voucher.entity;

import com.prgms.management.voucher.exception.InvalidVoucherParameterException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    public final static Integer MIN_PERCENT = 0;
    public final static Integer MAX_PERCENT = 100;
    private final Integer percent;
    private UUID voucherId;

    public PercentDiscountVoucher(Integer percent) {
        this(UUID.randomUUID(), percent);
    }

    public PercentDiscountVoucher(UUID voucherId, Integer percent) {
        if (percent < 0 || percent > 100) {
            throw new InvalidVoucherParameterException(MIN_PERCENT + "과 " + MAX_PERCENT + "사이의 값을 입력해주세요.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void resetVoucherId() {
        voucherId = UUID.randomUUID();
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * ((100 - percent) / 100);
    }

    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + percent;
    }

    @Override
    public String toString() {
        return "Percent Discount Voucher {" + "voucherId=" + voucherId + ", percent=" + percent + '}';
    }
}
