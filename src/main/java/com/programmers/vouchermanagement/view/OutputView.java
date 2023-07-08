package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;

import java.text.MessageFormat;
import java.util.List;

public class OutputView {

    private static final String VOUCHER_PROGRAM = "============= Voucher Program =============";
    private static final String DISCOUNT_TYPE = "=============== 바우처 할인 유형 ===============";
    private static final String VOUCHER_LIST = "================= 바우처 조회 =================";
    private static final String LINE = "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ";
    private static final String VOUCHER_TABLE_TITLE = " 번호 |                바우처 ID               | 할인 유형 | 할인 양 ";

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

    public static void showVouchers(List<VoucherResponse> vouchers) {
        System.out.println(VOUCHER_LIST);
        showVoucher(vouchers);
    }

    private static void showVoucher(List<VoucherResponse> vouchers) {
        System.out.println(LINE);
        System.out.println(VOUCHER_TABLE_TITLE);
        System.out.println(LINE);
        int i = 1;
        for (VoucherResponse voucher : vouchers) {
            System.out.println(MessageFormat.format("  {0}  | {1} | {2} |  {3}", i, voucher.getId(), voucher.getType().getName(), voucher.getAmount()));
            System.out.println(LINE);
            i++;
        }
    }
}
