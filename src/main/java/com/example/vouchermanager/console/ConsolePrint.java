package com.example.vouchermanager.console;

import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.exception.NotCorrectCommand;
import com.example.vouchermanager.exception.NotCorrectForm;
import com.example.vouchermanager.exception.NotCorrectScope;
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
            case "exit" -> {
                System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());

                return Command.EXIT;
            }
            default -> {
                log.error(LogMessage.NOT_CORRECT_COMMAND.getMessage());

                throw new NotCorrectCommand();
            }
        }
    }


    public VoucherType getVoucherType() throws NotCorrectForm {
        log.info(LogMessage.GET_VOUCHER_TYPE.getMessage());

        System.out.println(ConsoleMessage.GET_VOUCHER_TYPE.getMessage());
        String type = sc.nextLine();

        try {
            if (type.equals("fixed")) {
                return VoucherType.FIXED;
            } else if (type.equals("percent")) {
                return VoucherType.PERCENT;
            } else {
                throw new NotCorrectForm();
            }
        } catch (NotCorrectForm e) {
            log.error(LogMessage.NOT_CORRECT_FORM.getMessage());
            throw new NotCorrectForm();
        }
    }

    public long getVoucherDiscount(VoucherType type) throws NotCorrectScope, NotCorrectForm {
        log.info(LogMessage.GET_VOUCHER_DISCOUNT.getMessage());
        long discount = 0;
        try {
            if (type == VoucherType.FIXED) {
                System.out.println(ConsoleMessage.GET_DISCOUNT_AMOUNT.getMessage());
                discount = Long.parseLong(sc.nextLine());
            } else if (type == VoucherType.PERCENT) {
                System.out.println(ConsoleMessage.GET_DISCOUNT_PERCENT.getMessage());
                discount = Long.parseLong(sc.nextLine());
                if(discount < 0 || discount > 100) throw new NotCorrectScope();
            }
        } catch (NumberFormatException e) {
            log.error(LogMessage.NOT_CORRECT_FORM.getMessage());
            throw new NotCorrectForm();
        } catch (NotCorrectScope e) {
            log.error(LogMessage.NOT_CORRECT_SCOPE.getMessage());
            throw new NotCorrectScope();
        }

        return discount;
    }

    public void printCompleteCreate() {
        System.out.println(ConsoleMessage.COMPLETE_CREATE_VOUCHER.getMessage());
        System.out.println();
    }

    public void printList(List<Voucher> vouchers) {
        log.info(LogMessage.VOUCHER_LIST_PRINT.getMessage());

        vouchers.forEach(voucher -> {
            log.info(voucher.toString());
            System.out.println(voucher);
            System.out.println("---------------");
        });

        System.out.println();
    }
}
