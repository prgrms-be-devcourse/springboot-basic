package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;
import jline.Terminal;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;


public class Console<T extends TextTerminal>  implements Input, Output {
    private TextIO textIO;
    private T terminal;

    @Override
    public String readCommand(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public String readString(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public void println(String message) {
        terminal.println(message);
    }

    public void run(){
        consoleMenu();
        while (true){

        }
    }

    public void consoleMenu(){
        terminal.println("=== Voucher Program ===");
        terminal.println("Type exit to exit the program.");
        terminal.println("Type create to create a new voucher.");
        terminal.println("Type list to list all vouchers.");
    }

}
