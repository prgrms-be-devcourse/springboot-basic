package org.prgrms.kdt.devcourse.io;

import org.prgrms.kdt.devcourse.voucher.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class Console implements Input,Output {
    Scanner scanner = new Scanner(System.in);


    @Override
    public String input(String string) {
        System.out.println(string);
        return scanner.nextLine();
    }


    @Override
    public void inputError(String errorInput) {
        System.out.println(MessageFormat.format("{0} 은 잘못된 입력입니다. 다시 입력해주세요.",errorInput));
    }

    @Override
    public void printOut(String message) {
        System.out.println(message);
    }

    @Override
    public void printVoucherList(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            System.out.println("등록된 바우처가 없습니다.");
        } else {
            for (Voucher voucher : vouchers) {
                System.out.println("바우처 " + voucher.getVoucherId());
            }
        }
    }
}
