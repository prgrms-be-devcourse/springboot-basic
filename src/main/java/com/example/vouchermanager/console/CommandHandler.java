package com.example.vouchermanager.console;

import com.example.vouchermanager.domain.VoucherInfo;
import com.example.vouchermanager.exception.NotCorrectCommand;
import com.example.vouchermanager.message.ConsoleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandHandler {

    private final Scanner sc;

    @Autowired
    public CommandHandler(Reader reader) {
        this.sc = reader.sc;
    }

    public Command run() {
        System.out.println(ConsoleMessage.SELECT_FUNCTION.getMessage());

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

    public VoucherInfo getVoucherInfo() {
        System.out.println(ConsoleMessage.SELECT_VOUCHER_TYPE.getMessage());
        String type = sc.nextLine();
        VoucherType voucherType;

        if(type.equals("fixed")) {
            voucherType = VoucherType.FIXED;
        } else if(type.equals("percent")) {
            voucherType = VoucherType.PERCENT;
        } else {
            throw new NotCorrectCommand();
        }

        try {
            System.out.println(ConsoleMessage.GET_PRICE.getMessage());
            long price = Long.parseLong(sc.nextLine());
            return new VoucherInfo(voucherType, price);
        } catch (NumberFormatException | NotCorrectCommand e) {
            throw new NotCorrectCommand();
        }
    }
}
