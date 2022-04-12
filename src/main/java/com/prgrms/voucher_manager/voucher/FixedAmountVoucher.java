package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.io.Message;
import lombok.Builder;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final long amount;
    private final VoucherType type = VoucherType.FixedAmountVoucher;

    private static final long MAX_AMOUNT = 10000L;
    private static final long MIN_AMOUNT = 0L;

    public FixedAmountVoucher(UUID id, long amount) {
        if(amount > MAX_AMOUNT || amount < MIN_AMOUNT)
            throw new WrongVoucherValueException("0 ~ 10,000 사이의 수를 입력해야 합니다.");
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
