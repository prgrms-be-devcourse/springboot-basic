package org.prgms.order.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final static String type = "Percent";

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
    public long getAmount() {
        return percent;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String getVoucherInfo() {
        return getType() +", VoucherId = " + getVoucherId()+
                 ", Percent = " + getAmount()+"%";
    }

}
