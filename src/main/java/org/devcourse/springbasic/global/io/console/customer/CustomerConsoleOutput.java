package org.devcourse.springbasic.global.io.console.customer;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.devcourse.springbasic.global.io.output.customer.CustomerOutput;

public class CustomerConsoleOutput implements CustomerOutput {

    @Override
    public void menus() {

        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.printf("\n\n=== Customer Program ===\n" +
                "아래 메뉴 중 하나를 입력하세요.\n" +
                "1. exit (프로그램 종료)\n" +
                "2. signUp (회원가입)\n");
    }
}
