
package com.pgms.part1.domain.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class InitConsole {
    private TextIO textIO = TextIoFactory.getTextIO();

    public void init(){
        String command = textIO.newStringInputReader()
                .read("""
                         === Voucher Program ===
                        Type **exit** to exit the program.
                        Type **create** to create a new voucher.
                        Type **list** to list all vouchers.
                        """);

        System.out.println(command);
    }
}
