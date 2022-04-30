package com.programmers.part1.domain.voucher;

import com.programmers.part1.exception.voucher.VoucherTypeMissingException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {

    FIXED(FixedAmountVoucher::new),
    PERCENT(PercentAmountVoucher::new);

    private final BiFunction<UUID, Integer, Voucher> createInstance;

    VoucherType(BiFunction<UUID, Integer, Voucher> createInstance) {
        this.createInstance = createInstance;
    }

    public Voucher createVoucher(UUID voucherID, int amount){
        return this.createInstance.apply(voucherID,amount);
    }

    public static VoucherType getVoucherType(int request) throws VoucherTypeMissingException{
        return Arrays.stream(values())
                .filter(type -> type.ordinal() == request - 1)
                .findAny()
                .orElseThrow(() -> new VoucherTypeMissingException("Voucher Type이 잘못 입력되었습니다."));
    }
}
