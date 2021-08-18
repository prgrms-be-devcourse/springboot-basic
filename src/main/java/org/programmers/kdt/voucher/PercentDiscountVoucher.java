package org.programmers.kdt.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final int MAXIMUM_DISCOUNT_PERCENTAGE = 100;
    private final int MINIMUM_DISCOUNT_PERCENTAGE = 0;
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) {
            System.out.println("!! WARNING !!\nInvalid discount percentage : "
                            + percent
                            + "%\nAutomatically changed to " + MINIMUM_DISCOUNT_PERCENTAGE + "%");
            percent = 0;
        } else if ( percent > 100) {
            System.out.println("!! WARNING !!\nInvalid discount percentage : "
                    + percent
                    + "%\nAutomatically changed to " + MAXIMUM_DISCOUNT_PERCENTAGE + "%");
            percent = 100;
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (percent / 100.0d));
    }

    @Override
    public String toString() {
        return "<< Percent Discount Voucher >>\nID : " + this.voucherId + "\nDiscount : " + this.percent + "%";
    }

    @Override
    public long getDiscount() {
        return this.percent;
    }
}
