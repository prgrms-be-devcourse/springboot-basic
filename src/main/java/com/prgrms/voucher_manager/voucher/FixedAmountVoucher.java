package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.io.Message;
import lombok.Builder;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final long amount;
    private final VoucherType type = VoucherType.FixedAmountVoucher;

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
        return "Voucher type : " + type + ", amount : " + amount + ", voucherId :" + id;
    }

    @Override
    public VoucherType getVoucherType() {
        return type;
    }

    @Override
    public Long getValue() {
        return amount;
    }
}
