package com.prgrms.voucher_manager.voucher;

import lombok.Builder;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
public class PercentDiscountVoucher implements Voucher{

    private final UUID id;
    private final long percent;
    private final VoucherType type = VoucherType.PercentDiscountVoucher;

    public PercentDiscountVoucher(UUID id, long percent) {
        this.id = id;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherID() {
        return id;
    }

    @Override
    public String getInfo() {
        return "Voucher type : "+ type + ", percent : " + percent + "%, voucherId :" + id;
    }

    @Override
    public VoucherType getVoucherType() {
        return type;
    }

    @Override
    public Long getValue() {
        return percent;
    }
}
