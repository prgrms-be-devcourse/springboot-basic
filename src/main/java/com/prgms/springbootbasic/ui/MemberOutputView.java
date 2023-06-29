package com.prgms.springbootbasic.ui;

import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;
@Component
public class MemberOutputView {

    private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();

    public void initApplication() {
        textTerminal.println("=== Member Application ===");
        textTerminal.println("Type list to black list");
    }

}
