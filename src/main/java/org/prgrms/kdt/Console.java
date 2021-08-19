package org.prgrms.kdt;

import org.prgrms.kdt.IO.Input;
import org.prgrms.kdt.IO.Output;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String input(String guide) {
        System.out.println(guide);
        return scanner.nextLine();
    }

    @Override
    public void voucherCreateSuccess() {
        System.out.println("voucher 생성 성공");
    }

    @Override
    public void voucherCreateFail() {
        System.out.println("voucher 생성 실패");
    }

    @Override
    public void exit() {
        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void inputError(String message) {
        System.out.println(message);
    }


}
