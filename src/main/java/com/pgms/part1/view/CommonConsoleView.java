package com.pgms.part1.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonConsoleView {
    private final TextIO textIO = TextIoFactory.getTextIO();

    public String getMenu(){
        String command = textIO.newStringInputReader()
                .read("""
                         
                         === Voucher Program ===
                        Type **customer** to enter the customer menu.
                        Type **voucher** to enter the voucher menu.
                        Type **wallet** to enter the wallet menu.
                        Type **exit** to exit the program.
                        """);

        return command;
    }

    public void error(Exception e){
        System.out.println("\n" + e.getMessage());
    }
}
