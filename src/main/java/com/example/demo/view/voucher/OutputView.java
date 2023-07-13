package com.example.demo.view.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.List;

public class OutputView {

    public static final String STARTING_MESSAGE = "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
    public static final String COMMAND_OPTION_MESSAGE = "명령어를 입력해주세요 >> (exit, create, list): ";
    public static final String VOUCHER_OPTION_MESSAGE = "    Fixed Voucher를 발행하려면 fix를 입력해주세요.\n    Percent Voucher를 발행하려면 percent를 입력해주세요.\n    명령어를 입력해주세요 >> (fix, percent): ";
    public static final String FIXED_VOUCHER_AMOUNT_MESSAGE = "할인 금액을 입력해주세요 >> ";
    public static final String PERCENT_VOUCHER_AMOUNT_MESSAGE = "    1 이상 100 이하로 입력해주세요.\n    할인 비율을 입력해주세요 >> ";
    public static final String FIXED_VOUCHER_CREATE_MESSAGE = "    Fixed Voucher, Discount Amount: %.0f 할인권이 생성되었습니다.(바우처 ID : %s)";
    public static final String PERCENT_VOUCHER_CREATE_MESSAGE = "    Percent Voucher, Discount Percent Amount: %.0f 할인권이 생성되었습니다.(바우처 ID : %s)";
    public static final String VOUCHER_LIST_EMPTY_MESSAGE = "저장되어 있는 쿠폰이 없습니다.";


    public void printStartingMessage() {
        System.out.println(STARTING_MESSAGE);
    }

    public void printCommandOptionMessage() {
        System.out.print(COMMAND_OPTION_MESSAGE);
    }

    public void printVoucherOptionMessage() {
        System.out.print(VOUCHER_OPTION_MESSAGE);
    }

    public void printVoucherAmountInfoMessage(VoucherType voucherType) {
        System.out.print(voucherType.getVoucherAmountInfoMessage());
    }

    public void printCreateMessage(VoucherDto voucherDto) {
        switch (voucherDto.getVoucherType()) {
            case FIX -> System.out.println(String.format(FIXED_VOUCHER_CREATE_MESSAGE, voucherDto.getDiscountAmount(), voucherDto.getId().toString()));
            case PERCENT -> System.out.println(String.format(PERCENT_VOUCHER_CREATE_MESSAGE, voucherDto.getDiscountAmount(), voucherDto.getId().toString()));
            default -> throw new IllegalArgumentException("올바르지 않은 바우처 타입입니다. fix혹은 percent로 입력해주세요.");
        }
    }

    public void printVoucherList(List<VoucherDto> list) {
        if (list.isEmpty()) {
            System.out.println(VOUCHER_LIST_EMPTY_MESSAGE);
            return;
        }
        list.forEach(voucherDto -> System.out.println(voucherDto.formatAsString()));
    }
}
