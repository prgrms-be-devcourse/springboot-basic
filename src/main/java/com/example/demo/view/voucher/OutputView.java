package com.example.demo.view.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherDiscountType;
import java.util.List;

public class OutputView {


    public static final String COMMAND_OPTION_MESSAGE = "=== 바우처 관리 프로그램 ===\n= <1 ~ 4> 사이 숫자를 입력해주세요 =\n1. 바우처 생성\n2. 바우처 리스트 읽기\n3. 바우처 할인 금액 업데이트\n4. 종료\n\n입력 : ";
    public static final String VOUCHER_OPTION_MESSAGE = "    Fixed Voucher를 발행하려면 fix를 입력해주세요.\n    Percent Voucher를 발행하려면 percent를 입력해주세요.\n    명령어를 입력해주세요 >> (fix, percent): ";
    public static final String VOUCHER_ID_MESSAGE = "    바우처 ID를 입력해주세요 >> : ";
    public static final String FIXED_VOUCHER_CREATE_MESSAGE = "    Fixed Voucher, Discount Amount: %.0f 할인권이 생성되었습니다.(바우처 ID : %s)";
    public static final String PERCENT_VOUCHER_CREATE_MESSAGE = "    Percent Voucher, Discount Percent Amount: %.0f 할인권이 생성되었습니다.(바우처 ID : %s)";
    public static final String VOUCHER_LIST_EMPTY_MESSAGE = "저장되어 있는 쿠폰이 없습니다.";
    public static final String VOUCHER_UPDATE_MESSAGE = "    바우처 할인액이 정상적으로 업데이트되었습니다.";
    public static final String VOUCHER_DELETE_MESSAGE = "    바우처가 정상적으로 삭제되었습니다.";

    public void printVoucherCommandOptionMessage() {
        System.out.print(COMMAND_OPTION_MESSAGE);
    }

    public void printVoucherIdMessage() {
        System.out.print(VOUCHER_ID_MESSAGE);
    }

    public void printVoucherOptionMessage() {
        System.out.print(VOUCHER_OPTION_MESSAGE);
    }

    public void printVoucherAmountInfoMessage(VoucherDiscountType voucherDiscountType) {
        System.out.print(voucherDiscountType.getVoucherAmountInfoMessage());
    }

    public void printCreateMessage(VoucherDto voucherDto) {
        switch (voucherDto.getVoucherDiscountType()) {
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

    public void printUpdateMessage() {
        System.out.println(VOUCHER_UPDATE_MESSAGE);
    }

    public void printDeleteMessage() {
        System.out.println(VOUCHER_DELETE_MESSAGE);
    }
}
