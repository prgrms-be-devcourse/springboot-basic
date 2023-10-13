package com.example.vouchermanager.console;

import com.example.vouchermanager.exception.NotCorrectCommand;
import com.example.vouchermanager.message.ConsoleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandHandler {

    private Scanner sc;

    @Autowired
    public CommandHandler(Reader reader) {
        this.sc = reader.sc;
    }

    public Command run() {
        System.out.println(ConsoleMessage.SELECT_FUNCTION);

        switch (sc.nextLine()) {
            case "create" -> {
                return Command.CREATE;
            }
            case "list" -> {
                return Command.LIST;
            }
            default -> {
                throw new NotCorrectCommand();
            }
        }
    }

    public VoucherType selectVoucherType() {
        System.out.println(ConsoleMessage.SELECT_VOUCHER_TYPE);

        if(sc.nextLine().equals("fixed")) return VoucherType.FIXED;
        else if(sc.nextLine().equals("percent")) return VoucherType.PERCENT;
        else {
            throw new NotCorrectCommand();
        }
    }
}
