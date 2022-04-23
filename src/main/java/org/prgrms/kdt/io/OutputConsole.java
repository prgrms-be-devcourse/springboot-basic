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
                "Type update to update a voucher.\n" +
                "Type delete to delete a voucher.\n" +
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
    public void printVoucherType() {
        StringBuilder stringBuilder = new StringBuilder("바우처 타입을 선택해주세요.\n");
        stringBuilder.append(VoucherType.getAllVoucherManual());
        printMessage(stringBuilder.toString());
    }

    @Override
    public void printVoucherUpdateManual() {
        printMessage("수정할 바우처 ID를 입력해주세요.");
    }

    @Override
    public void printVoucherUpdateValue() {
        printMessage("수정할 바우처 값을 입력해주세요.");
    }

    @Override
    public void printVoucherDeleteManual() {
        printMessage("삭제할 바우처 값을 입력해주세요.");
    }

    @Override
    public void printVoucherValue(VoucherType voucherType) {
        StringBuilder stringBuilder = new StringBuilder("바우처 값을 입력해주세요.\n");
        stringBuilder.append(voucherType.getVoucherValidationMessage());
        printMessage(stringBuilder.toString());
    }

    @Override
    public void printVoucherCreateSuccess(String voucherInfo) {
        printMessage("바우처 생성에 성공하였습니다. Voucher: " + voucherInfo);
    }

    @Override
    public void printVoucherUpdateSuccess(String voucherInfo) {
        printMessage("바우처 수정에 성공하였습니다. Voucher: " + voucherInfo);
    }

    @Override
    public void printVoucherDeleteSuccess() {
        printMessage("바우처 삭제에 성공하였습니다.");
    }

}
