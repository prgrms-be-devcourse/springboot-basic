package com.example.vouchermanager.console;

import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.domain.VoucherInfo;
import com.example.vouchermanager.exception.NotCorrectCommand;
import com.example.vouchermanager.message.ConsoleMessage;
import com.example.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class ConsolePrint {

    private final Scanner sc;

    @Autowired
    public ConsolePrint(Reader reader) {
        this.sc = reader.sc;
    }

    public Command run() {
        log.info(LogMessage.SELECT_FUNCTION.getMessage());
        System.out.println(ConsoleMessage.SELECT_FUNCTION.getMessage());

        switch (sc.nextLine()) {
            case "create" -> {
                log.info(LogMessage.SELECT_CREATE.getMessage());

                return Command.CREATE;
            }
            case "list" -> {
                log.info(LogMessage.SELECT_LIST.getMessage());

                return Command.LIST;
            }
            default -> {
                log.info(LogMessage.NOT_CORRECT_COMMAND.getMessage());

                throw new NotCorrectCommand();
            }
        }
    }

    public VoucherInfo getVoucherInfo() {
        log.info(LogMessage.GET_VOUCHER_INFO.getMessage());

        System.out.println(ConsoleMessage.SELECT_VOUCHER_TYPE.getMessage());
        String type = sc.nextLine();
        VoucherType voucherType;

        if(type.equals("fixed")) {
            voucherType = VoucherType.FIXED;
        } else if(type.equals("percent")) {
            voucherType = VoucherType.PERCENT;
        } else {
            log.error(LogMessage.NOT_CORRECT_COMMAND.getMessage());
            throw new NotCorrectCommand();
        }

        log.info(LogMessage.VOUCHER_TYPE_INFO.getMessage(), voucherType.getType());

        try {
            System.out.println(ConsoleMessage.GET_PRICE.getMessage());
            long price = Long.parseLong(sc.nextLine());

            log.info(LogMessage.VOUCHER_PRICE_INFO.getMessage(), price);
            return new VoucherInfo(voucherType, price);
        } catch (NumberFormatException | NotCorrectCommand e) {
            log.error(LogMessage.NOT_CORRECT_FORM.getMessage());
            throw new NotCorrectCommand();
        }
    }

    public void printList(List<Voucher> vouchers) {
        log.info(LogMessage.VOUCHER_LIST_PRINT.getMessage());
        vouchers.forEach(voucher -> {
            log.info(LogMessage.VOUCHER_INFO.getMessage(), voucher.toString());
            System.out.println(voucher.toString());
        });
    }
}
