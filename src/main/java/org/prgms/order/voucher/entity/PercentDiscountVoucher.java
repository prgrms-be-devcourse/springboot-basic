package org.prgms.order.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        if(percent>=100){
            this.percent = 100;

        }else if(percent <=0){
            this.percent =0;

        }else {
            this.percent= percent;
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String getVoucherInfo() {
        return "PercentDiscountVoucher " +
                 ", Percent = " + percent+"%";
    }

}
