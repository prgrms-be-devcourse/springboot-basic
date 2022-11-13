package org.prgrms.voucherapplication.entity;

import org.prgrms.voucherapplication.common.VoucherException;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    private static final String PERCENT_NOT_100 = "할인 퍼센트는 100% 이하만 가능합니다.";

    public PercentDiscountVoucher(UUID uuid, int percent) {
        super(uuid, percent);

        if (percent > 100) {
            throw new VoucherException(PERCENT_NOT_100);
        }
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "uuid=" + uuid +
                ", discount=" + discount +
                "}\n";
    }
}
