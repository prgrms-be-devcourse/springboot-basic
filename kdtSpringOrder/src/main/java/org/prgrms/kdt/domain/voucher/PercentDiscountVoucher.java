package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.enums.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID customerId;
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID customerId, UUID voucherId, long percent, VoucherType voucherType) {
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getCustomerId() {return customerId; }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount(){
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public VoucherType getVoucherType(){ return voucherType; };

    @Override
    public String toString() {
        return MessageFormat.format("Percent Discount Voucher [ customerId = {0}, voucherId = {1}, percent = {2} ]", customerId, voucherId, percent);
    }

}