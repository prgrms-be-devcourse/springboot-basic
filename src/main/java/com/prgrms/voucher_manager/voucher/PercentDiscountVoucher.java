package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import lombok.Builder;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
public class PercentDiscountVoucher implements Voucher{

    private final UUID id;
    private final long percent;
    private final VoucherType type = VoucherType.PercentDiscountVoucher;

    private static final long MAX_PERCENT = 100L;
    private static final long MIN_PERCENT = 0L;

    public PercentDiscountVoucher(UUID id, long percent) {
        if(percent < MIN_PERCENT || percent > MAX_PERCENT)
            throw new WrongVoucherValueException("0 ~ 100 사이의 수를 입력해야 합니다.");
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
