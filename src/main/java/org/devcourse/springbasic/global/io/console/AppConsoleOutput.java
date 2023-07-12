package org.devcourse.springbasic.global.io.console;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.devcourse.springbasic.global.io.output.AppOutput;

public class AppConsoleOutput implements AppOutput {

    @Override
    public void menus() {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.printf("\n\n=== Voucher Program ===\n" +
                "아래 메뉴 중 하나를 입력하세요.\n" +
                "1. 회원\n" +
                "2. 바우처\n");
    }
}
