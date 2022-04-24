package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
public class PercentDiscountVoucher implements Voucher{

    private final UUID id;
    private long percent;

    private static final long MAX_PERCENT = 100L;
    private static final long MIN_PERCENT = 0L;

    public PercentDiscountVoucher(UUID id, long percent) {
        validateValue(percent);
        this.id = id;
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (long) (beforeDiscount * ((double) percent / 100));
    }

    @Override
    public UUID getVoucherID() {
        return id;
    }

    @Override
    public void validateValue(Long percent) {
        if(percent < MIN_PERCENT || percent > MAX_PERCENT)
            throw new WrongVoucherValueException("0 ~ 100 사이의 수를 입력해야 합니다.");
    }


    @Override
    public Long getValue() {
        return percent;
    }

    @Override
    public void changeValue(Long value) {
        validateValue(value);
        this.percent = value;
    }

    @Override
    public String toString() {
        return "type=PercentDiscountVoucher" +
                ",id=" + id +
                ",percent=" + percent +
                "\n";
    }
}
