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

    public VoucherInfo getVoucherInfo() {
        System.out.println(ConsoleMessage.SELECT_VOUCHER_TYPE);
        String type = sc.nextLine();
        VoucherType voucherType;

        if(type.equals("fixed")) {
            voucherType = VoucherType.FIXED;
            System.out.println(ConsoleMessage.GET_DISCOUNT_AMOUNT);
        } else if(type.equals("percent")) {
            voucherType = VoucherType.PERCENT;
            System.out.println(ConsoleMessage.GET_DISCOUNT_PERCENT);
        } else {
            throw new NotCorrectCommand();
        }

        try {
            long discount = Long.parseLong(sc.nextLine());
            if(voucherType == VoucherType.PERCENT && !(discount >= 0 && discount <= 100)) {
                throw new NotCorrectCommand();
            }
            return new VoucherInfo(voucherType, discount);
        } catch (NumberFormatException | NotCorrectCommand e) {
            throw new NotCorrectCommand();
        }
    }
}
