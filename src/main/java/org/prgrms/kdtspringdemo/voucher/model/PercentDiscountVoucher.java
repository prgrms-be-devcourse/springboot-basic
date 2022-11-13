package org.prgrms.kdtspringdemo.voucher.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_PERCENT = 100;
    private static final long MIN_PERCENT = 0;
    private final  UUID voucherId;

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) throws IllegalArgumentException{
        if(!voucherAllow(percent)) {
            throw new IllegalArgumentException("허용되지 않는 숫자입니다.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }
    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount *(percent/100);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public String discountValue() {
        return String.valueOf(percent);
    }

    @Override
    public CsvDto makeCsvDtoFromVoucher() {
        List<String[]> lines = new ArrayList<>();
        String[] line = {this.voucherId.toString(),String.valueOf(this.percent),this.getVoucherType().name()};
        lines.add(line);
        return CsvDto.from(lines);
    }

    private boolean voucherAllow(Long value) {
        return value >= MIN_PERCENT && value <= MAX_PERCENT;
    }
}
