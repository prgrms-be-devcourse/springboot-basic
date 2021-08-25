package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final int MAXIMUM_DISCOUNT_PERCENT = 100;
    private final int MINIMUM_DISCOUNT_PERCENT = 0;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        // TODO : 워닝 메세지를 로그로 출력하도록 변경
        if (percent < MINIMUM_DISCOUNT_PERCENT) {
            System.out.println(
                    MessageFormat.format(
                            "!! WARNING !!\nInvalid discount percentage : {0}\n Automatically changed to {1}%",
                            percent, MINIMUM_DISCOUNT_PERCENT));
            percent = MINIMUM_DISCOUNT_PERCENT;
        } else if (percent > MAXIMUM_DISCOUNT_PERCENT) {
            System.out.println(
                    MessageFormat.format(
                            "!! WARNING !!\nInvalid discount percentage : {0}\n Automatically changed to {1}%",
                            percent, MAXIMUM_DISCOUNT_PERCENT));
            percent = MAXIMUM_DISCOUNT_PERCENT;
        }
        this.percent = percent;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return percent;
    }

    @Override
    public long getDiscountAmount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return MessageFormat.format("<< Percent Discount Voucher >> \nID : {0}\nDiscount : {1}%", voucherId, percent);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || o.getClass() != PercentDiscountVoucher.class) {
            return false;
        }
        PercentDiscountVoucher voucher = (PercentDiscountVoucher)o;
        return voucherId.equals(voucher.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voucherId);
    }

}
