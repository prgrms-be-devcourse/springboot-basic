package org.prgrms.springbootbasic.type;

import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.factory.FixedAmountVoucherFactory;
import org.prgrms.springbootbasic.factory.PercentAmountVoucherFactory;
import org.prgrms.springbootbasic.factory.VoucherFactory;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FIXED("1", "amount", FixedAmountVoucherFactory.class, FixedAmountVoucher.class),
    PERCENT("2", "percent", PercentAmountVoucherFactory.class, PercentAmountVoucher.class);

    private final String number;
    private final String argument;
    private final Class<? extends VoucherFactory> voucherFactoryClass;
    private final Class<? extends Voucher> voucherClass;


    VoucherType(String number, String argument, Class<? extends VoucherFactory> voucherFactoryClass, Class<? extends Voucher> voucherClass) {
        this.number = number;
        this.argument = argument;
        this.voucherFactoryClass = voucherFactoryClass;
        this.voucherClass = voucherClass;
    }

    private String getNumber() {
        return number;
    }

    public String getArgument() {
        return argument;
    }

    public Class<? extends VoucherFactory> getVoucherFactoryClass() {
        return voucherFactoryClass;
    }

    public Class<? extends Voucher> getVoucherClass() {
        return voucherClass;
    }

    public static VoucherType NumberToVoucherType(String option) {
        return Arrays.stream(VoucherType.values())
                .filter(x -> x.getNumber().equals(option))
                .findFirst().get();
    }

    public static VoucherType ClassToVoucherType(Voucher voucher) {
        return Arrays.stream(VoucherType.values())
                .filter(x -> Objects.equals(x.getVoucherClass(), voucher.getClass()))
                .findFirst().get();
    }

    public static boolean isFixed(String option) {
        return FIXED.getNumber().equals(option);
    }

    public static boolean isPercent(String option) {
        return PERCENT.getNumber().equals(option);
    }

}
