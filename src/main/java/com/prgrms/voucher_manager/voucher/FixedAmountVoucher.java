package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.io.Message;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private long amount;

    private static final long MAX_AMOUNT = 10000L;
    private static final long MIN_AMOUNT = 0L;

    public FixedAmountVoucher(UUID id, long amount) {
        validateValue(amount);
        this.id = id;
        this.amount = amount;
    }


    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0 ) ? 0 : discountedAmount;

    }

    @Override
    public UUID getVoucherId() {
        return id;
    }

    @Override
    public void validateValue(Long amount) {
        if(amount > MAX_AMOUNT || amount < MIN_AMOUNT)
            throw new WrongVoucherValueException("0 ~ 10,000 사이의 수를 입력해야 합니다.");

    }



    @Override
    public Long getValue() {
        return amount;
    }

    @Override
    public String getType() {
        return "FixedAmountVoucher";
    }

    @Override
    public void changeValue(Long value) {
        validateValue(value);
        this.amount = value;
    }

    @Override
    public String toString() {
        return "type=FixedAmountVoucher" +
                ",id=" + id +
                ",amount=" + amount +
                "\n";
    }
}
