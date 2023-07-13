package com.example.demo.view.voucher;

import com.example.demo.dto.voucher.VoucherResponseDto;
import com.example.demo.util.VoucherType;
import java.util.List;

public class OutputView {

    public static final String COMMAND_OPTION_MESSAGE = "=== 바우처, 고객 관리 프로그램 ===\n= <1 ~ 7> 사이 숫자를 입력해주세요 =\n1. 바우처 생성\n2. 바우처 리스트 출력\n3. 고객 생성\n4. 고객 리스트 출력\n5. 고객 이름 업데이트\n6. 고객 삭제\n7. 프로그램 종료\n\n입력 : ";
    public static final String VOUCHER_OPTION_MESSAGE = "    Fixed Voucher를 발행하려면 fix를 입력해주세요.\n    Percent Voucher를 발행하려면 percent를 입력해주세요.\n    명령어를 입력해주세요 >> (fix, percent): ";
    public static final String FIXED_VOUCHER_CREATE_MESSAGE = "    Fixed Voucher, Discount Amount: %.0f 할인권이 생성되었습니다.(바우처 ID : %s)";
    public static final String PERCENT_VOUCHER_CREATE_MESSAGE = "    Percent Voucher, Discount Percent Amount: %.0f 할인권이 생성되었습니다.(바우처 ID : %s)";
    public static final String VOUCHER_LIST_EMPTY_MESSAGE = "저장되어 있는 쿠폰이 없습니다.";


    public void printCommandOptionMessage() {
        System.out.print(COMMAND_OPTION_MESSAGE);
    }

    public void printVoucherOptionMessage() {
        System.out.print(VOUCHER_OPTION_MESSAGE);
    }

    public void printVoucherAmountInfoMessage(VoucherType voucherType) {
        System.out.print(voucherType.getVoucherAmountInfoMessage());
    }

    public void printCreateMessage(VoucherResponseDto voucherResponseDto) {
        switch (voucherResponseDto.getVoucherType()) {
            case FIX -> System.out.println(String.format(FIXED_VOUCHER_CREATE_MESSAGE, voucherResponseDto.getDiscountAmount(), voucherResponseDto.getId().toString()));
            case PERCENT -> System.out.println(String.format(PERCENT_VOUCHER_CREATE_MESSAGE, voucherResponseDto.getDiscountAmount(), voucherResponseDto.getId().toString()));
            default -> throw new IllegalArgumentException("올바르지 않은 바우처 타입입니다. fix혹은 percent로 입력해주세요.");
        }
    }

    public void printVoucherList(List<VoucherResponseDto> list) {
        if (list.isEmpty()) {
            System.out.println(VOUCHER_LIST_EMPTY_MESSAGE);
            return;
        }
        list.forEach(voucherDto -> System.out.println(voucherDto.formatAsString()));
    }
}
