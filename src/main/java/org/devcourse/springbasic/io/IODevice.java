package org.devcourse.springbasic.io;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.devcourse.springbasic.controller.menu.MenuType;
import org.devcourse.springbasic.voucher.Voucher;
import org.devcourse.springbasic.voucher.VoucherType;

import java.util.List;

public interface IODevice {

    MenuType inputMenu();
    VoucherType inputVoucherMenu();
    void outputVoucher(Voucher voucher);
    void outputVouchers(List<Voucher> vouchers);

    default void outputMenus() {

        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.printf("\n\n=== Voucher Program ===\n" +
                "아래 메뉴 중 하나를 입력하세요.\n" +
                "1. exit (프로그램 종료)\n" +
                "2. create (새로운 바우처 생성)\n" +
                "3. list (모든 바우처 확인)\n");
    }

    default void outputVoucherMenus() {

        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.printf(
                "\n(바우처 선택) 아래 메뉴 중 하나를 입력하세요.\n" +
                "1. Fixed amount voucher\n" +
                "2. Percent discount voucher\n");
    }
}
