package org.prgrms.kdt.io;

import org.prgrms.kdt.model.voucher.VoucherType;

public class OutputConsole implements Output {

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printCommandManual() {
        printMessage(
            "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n" +
            "Type black-list to list all customer in black-list."
        );
    }

    @Override
    public void printInvalidCommand() {
        printMessage("유효하지 않은 명령입니다.");
    }

    @Override
    public void printShutDownSystem() {
        printMessage("시스템이 종료되었습니다.");
    }

    @Override
    public void printVoucherManual() {
        printMessage(
            "바우처 타입을 선택해주세요.\n" +
            "Type fixed to create a new FixedAmountVoucher\n" +
            "Type percent to create a new PercentDiscountVoucher."
        );
    }

    @Override
    public void printVoucherValue(VoucherType voucherType) {
        StringBuilder stringBuilder = new StringBuilder("바우처 값을 입력해주세요.\n");
        if (voucherType == VoucherType.FIXED_AMOUNT) {
            stringBuilder.append("(0 초과로 입력해주세요.)");
        } else if (voucherType == VoucherType.PERCENT_DISCOUNT) {
            stringBuilder.append("(0 초과, 100이하로 입력해주세요.)");
        }
        printMessage(stringBuilder.toString());
    }

    @Override
    public void printVoucherCreateSuccess(String voucherInfo) {
        printMessage("바우처 생성에 성공하였습니다. Voucher: " + voucherInfo);
    }

}
