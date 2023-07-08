package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.voucher.application.VoucherDto;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;

import java.text.MessageFormat;
import java.util.List;

public class OutputView {

    private static final String VOUCHER_PROGRAM = "============= Voucher Program =============";
    private static final String DISCOUNT_TYPE = "=============== 바우처 할인 유형 ===============";
    private static final String VOUCHER_LIST = "================= 바우처 조회 =================";

    public static void showCommand() {
        System.out.println(VOUCHER_PROGRAM);
        for (Command command : Command.values()) {
            System.out.println(MessageFormat.format("({0}) {1} : {2}", command.getNumber(), command, command.getDescription()));
        }
    }

    public static void showDiscountType() {
        System.out.println(DISCOUNT_TYPE);
        for (DiscountType discountType : DiscountType.values()) {
            System.out.println(MessageFormat.format("({0}) {1} : {2}", discountType.getNumber(), discountType, discountType.getName()));
        }
    }

    public static void showVouchers(List<VoucherDto> vouchers) {
        System.out.println(VOUCHER_LIST);
        for (VoucherDto voucher : vouchers) {
            System.out.println(MessageFormat.format("{0} : {1}", voucher.discountType().getName(), voucher.amount()));
        }
    }
}
