package com.kdt.commandLineApp.console;

import com.kdt.commandLineApp.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuMainLogic {
    private IO io;

    @Autowired
    public MenuMainLogic(IO io) {
        this.io = io;
    }

    public void printMainMenu() {
        io.print("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher\n" +
                "Type list to list all vouchers.\n" +
                "Type blacklist to list all blacklist custom info.\n"+
                "========================\n"+
                "Type give voucher to give voucher to custmer.\n"+
                "Type take voucher to take a voucher from customer.\n"+
                "Type customer list to see customer list with same voucher.\n"+
                "Type voucher list to see voucher list of customer.\n"
        );
    }

    public Command getCommand() throws Exception {
        return Command.fromString(io.get());
    }
}
