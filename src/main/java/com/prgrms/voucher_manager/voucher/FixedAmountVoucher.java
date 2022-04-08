package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.io.Message;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final long amount;

    public FixedAmountVoucher(UUID id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherID() {
        return id;
    }

    @Override
    public String getInfo() {
        return "Voucher type : FixedAmount / amount : " + amount + " / id :" + id;
    }
}
