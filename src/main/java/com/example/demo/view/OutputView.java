package com.example.demo.view;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.List;

public class OutputView {

    public static final String STARTING_MESSAGE = "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
    public static final String COMMAND_OPTION_MESSAGE = "명령어를 입력해주세요 >> (exit, create, list): ";
    public static final String VOUCHER_OPTION_MESSAGE = "    Fixed Amount Voucher를 발행하려면 fix를 입력해주세요.\n    Percent Voucher를 발행하려면 percent를 입력해주세요.\n    명령어를 입력해주세요 >> (fix, percent): ";
    public static final String FIXED_VOUCHER_AMOUNT_MESSAGE = "할인 금액을 입력해주세요 >> ";
    public static final String PERCENT_VOUCHER_AMOUNT_MESSAGE = "    0 이상 100 이하로 입력해주세요.\n    할인 비율을 입력해주세요 >> ";


    public void printStartingMessage() {
        System.out.println(STARTING_MESSAGE);
    }

    public void printCommandOptionMessage() {
        System.out.println(COMMAND_OPTION_MESSAGE);
    }

    public void printVoucherOptionMessage() {
        System.out.println(VOUCHER_OPTION_MESSAGE);
    }

    public void printVoucherAmountInfoMessage(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> System.out.println(FIXED_VOUCHER_AMOUNT_MESSAGE);
            case PERCENT_DISCOUNT_VOUCHER -> System.out.println(PERCENT_VOUCHER_AMOUNT_MESSAGE);
        }
    }

    public void printVoucherList(List<VoucherDto> list) {

    }
}
