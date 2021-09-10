package org.programmers.applicationcontext;

import org.programmers.applicationcontext.voucher.Voucher;

import java.util.List;

public class OutPutView {

    public void selectMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("====== Voucher Program ======\n");
        sb.append("Type exit to exit the program.\n");
        sb.append("Type create to create a new voucher.\n");
        sb.append("Type list to list all vouchers.");
        System.out.println(sb);
    }

    public void selectError() {
        System.out.println("잘못된 입력입니다. 다시 입력해주세요");
    }

    public void selectVoucherType() { // StringBuffer를 쓰라는 말은 이런 식으로 나누라는 의미
        StringBuffer sb = new StringBuffer();
        sb.append("1. FixedAmountVoucher\n");
        sb.append("2. PercentDiscountVoucher\n");
        sb.append("생성하고 싶은 Voucher의 종류를 숫자로 쓰시오\n");
        sb.append("ex) 1");
        System.out.println(sb);
    }

    public void terminateProgram() {
        System.out.println("프로그램을 종료합니다");
    }

    public void showAllVouchers(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("Voucher 종류: " + voucher.getClass().getName());
            System.out.println("바우처 아이디 = " + voucher.getVoucherId());
            System.out.println("바우처 할인가격(비율) = " + voucher.getVoucherVolume());
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    public void inputFixedAmountVoucher() {
        System.out.println("FixedAmountVoucher에 부여할 할인값을 작성하시오");
    }

    public void inputPercentAmountVoucher() {
        System.out.println("FixedAmountVoucher에 부여할 할인값을 작성하시오");
    }
}
