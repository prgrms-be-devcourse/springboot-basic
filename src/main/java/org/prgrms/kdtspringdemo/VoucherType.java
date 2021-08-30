package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.Voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT("F"), PERCENT_DISCOUNT("P");

    private final String inputCommand;

    VoucherType(String p) {
        this.inputCommand = p;
    }

    public static boolean isInVoucherType(String command) {
        long count = Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.inputCommand.equals(command))
                .count();
        return count != 0;
    }

    public static VoucherType findByCommand(String command) {
        Optional<VoucherType> voucherType = Arrays.stream(VoucherType.values())
                .filter(type -> type.inputCommand.equals(command))
                .findFirst();
        return voucherType.orElse(null);
    }

    public static Voucher createVoucher(String inputCommand, long value) {
        switch (findByCommand(inputCommand)) {
            case FIXED_AMOUNT -> {
                if (value > 0 && value <= 100000) return new FixedAmountVoucher(UUID.randomUUID(), value);
                else return null;
            }
            case PERCENT_DISCOUNT ->  {
                if (value > 0 && value <= 100) return new PercentDiscountVoucher(UUID.randomUUID(), value);
                else return null;
            }
        }
        return null;
    }

    ;
};
