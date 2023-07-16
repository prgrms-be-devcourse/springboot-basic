package com.example.voucher.constant;

import static com.example.voucher.constant.ExceptionMessage.*;

import java.util.Arrays;
import java.util.UUID;
import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;

public enum VoucherType {

    FIXED_AMOUNT_DISCOUNT(1) {
        @Override
        public Voucher createVoucher(Long discountValue) {
            return new FixedAmountVoucher(discountValue);
        }

        @Override
        public Voucher createVoucher(UUID voucherID, Long discountValue) {
            return new FixedAmountVoucher(voucherID, discountValue);
        }
    },
    PERCENT_DISCOUNT(2) {
        @Override
        public Voucher createVoucher(Long discountValue) {
            return new PercentDiscountVoucher(discountValue);
        }

        @Override
        public Voucher createVoucher(UUID voucherID, Long discountValue) {
            return new PercentDiscountVoucher(voucherID, discountValue);
        }
    };

    private final int inputNum;

    VoucherType(int inputNum) {
        this.inputNum = inputNum;
    }

    public int getInputNum() {
        return inputNum;
    }

    public abstract Voucher createVoucher(Long discountValue);

    public abstract Voucher createVoucher(UUID voucherID, Long discountValue);

    public static VoucherType getVouchersType(int inputVoucherType) {
        return Arrays.stream(values())
            .filter(v -> inputVoucherType == v.getInputNum())
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_CANT_CREATE_VOUCHER));
    }

}
