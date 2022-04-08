package com.prgrms.voucher_manager.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final UUID id;
    private final long percent;

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
        return "Voucher type : PercentDiscount / percent : " + percent + " / id :" + id;
    }
}
