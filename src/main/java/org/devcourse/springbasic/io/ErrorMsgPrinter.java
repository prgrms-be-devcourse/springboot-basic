package org.devcourse.springbasic.io;

import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ErrorMsgPrinter {

    private static final TextTerminal<?> TERMINAL = TextIoFactory.getTextIO().getTextTerminal();

    public static void print(String msg) {
        TERMINAL.printf(msg);
    }
}
