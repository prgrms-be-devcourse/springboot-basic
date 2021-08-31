package org.prgms.order.voucher.entity;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Pattern;

public class FixedAmountVoucher implements Voucher {
    private final static String type = "Fixed";
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public String getType() {
        return type;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }

    @Override
    public String getVoucherInfo() {
        return MessageFormat.format("{0}, VoucherId = {1}, Discount = {2}", getType(), getVoucherId(), getAmount());
    }

}
