package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.reader.ReadException;
import org.weekly.weekly.ui.writer.CommandWriter;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.util.VoucherMenu;

import java.io.IOException;

@Component
public class CommandLineApplication {
    private final CommandReader commandReader;
    private final CommandWriter commandWriter;

    @Autowired
    public CommandLineApplication(CommandReader commandReader, CommandWriter commandWriter) {
        this.commandReader = commandReader;
        this.commandWriter = commandWriter;
    }

    public VoucherMenu readMenu() {
        while(true) {
            try {
                this.commandWriter.printVoucherProgram();
                return VoucherMenu.getMenu(this.commandReader.readLine());
            } catch (Exception exception) {
                this.commandWriter.printErrorMsg(ExceptionMsg.NOT_MENU.getMsg());
            }
        }
    }



}
