package com.prgrms.kdt.springbootbasic;

import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public enum VoucherList {
    FIXED_AMOUNT_VOUCHER("Fixed"){
        @Override
        public Voucher getConstructor(UUID voucherId, Long discountAmount) {
            return new FixedAmountVoucher(voucherId, discountAmount);
        }

        @Override
        public Voucher makeVoucher(UUID voucherId, Long discountAmount, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, discountAmount, createdAt);
        }

    },
    PERCENT_DISCOUNT_VOUCHER("Percent"){
        @Override
        public Voucher getConstructor(UUID voucherId,Long discountAmount) {
            return new PercentDiscountVoucher(voucherId, discountAmount);
        }

        @Override
        public Voucher makeVoucher(UUID voucherId, Long discountAmount, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, discountAmount,createdAt);
        }
    };

    private final String className;
    VoucherList(String className){
        this.className = className;
    }

    public String getClassName(){ return className;}

    public static VoucherList getVoucherType(String className){
        return Arrays.stream(VoucherList.values()).filter(v -> v.className.equals(className)).collect(Collectors.toList()).get(0);
    }

    public static Voucher makeVoucherByType(String voucherType, UUID voucherId, long amount, LocalDateTime createdAt) {
        return Arrays.stream(VoucherList.values()).filter(v -> v.className.equals(voucherType)).collect(Collectors.toList()).get(0).makeVoucher(voucherId, amount, createdAt);
    }

    public abstract Voucher getConstructor(UUID voucherId,Long discountAmount);


    public abstract Voucher makeVoucher(UUID voucherId, Long discountAmount, LocalDateTime createdAt);
}
