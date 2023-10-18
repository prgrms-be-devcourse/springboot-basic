package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.controller.VoucherController;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectScope;
import com.prgrms.vouchermanager.io.Command;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class CommandHandler {
    private final ConsolePrint consolePrint;
    private final VoucherController controller;
    private final Scanner sc = new Scanner(System.in);

    public CommandHandler(ConsolePrint consolePrint, VoucherController controller) {
        this.consolePrint = consolePrint;
        this.controller = controller;
    }

    public Command getCommand() throws NotCorrectCommand {
        consolePrint.printFunctionSelect();
        String command = sc.nextLine();
        switch (command) {
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
            case "blacklist" -> {
                log.info(LogMessage.SELECT_BLACKLIST.getMessage());

                return Command.BLACKLIST;
            }
            default -> throw new NotCorrectCommand();
        }
    }

    public void createExecute() throws NotCorrectForm, NotCorrectScope {
        VoucherType voucherType = getVoucherType();
        long discount = getVoucherDiscount(voucherType);

        log.info(LogMessage.VOUCHER_TYPE_AND_DISCOUNT.getMessage(), voucherType.getType(), discount);

        controller.create(voucherType, discount);
        consolePrint.printCompleteCreate();
    }

    public void listExecute() throws EmptyListException {
        List<Voucher> vouchers = controller.list();
        if(vouchers.isEmpty()) throw new EmptyListException();
        else consolePrint.printList(controller.list());
    }

    public void exitExecute() {
        log.info(LogMessage.FINISH_PROGRAM.getMessage());

        System.exit(0);
    }

    public void blackListExecute() throws EmptyListException{
        List<Customer> customers = controller.blacklist();
        if(customers.isEmpty()) throw new EmptyListException();
        else consolePrint.printBlacklist(controller.blacklist());
    }

    public VoucherType getVoucherType() throws NotCorrectForm {
        consolePrint.printGetVoucherType();
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
            throw new NotCorrectForm();
        }
    }

    public long getVoucherDiscount(VoucherType type) throws NotCorrectScope, NotCorrectForm {
        long discount = 0;
        try {
            if (type == VoucherType.FIXED) {
                discount = getFixedDiscount();
            } else if (type == VoucherType.PERCENT) {
                discount = getPercentDiscount();
            }
        } catch (NumberFormatException e) {
            throw new NotCorrectForm();
        } catch (NotCorrectScope e) {
            throw new NotCorrectScope();
        }

        return discount;
    }

    private long getPercentDiscount() throws NotCorrectScope {
        long discount;
        consolePrint.printGetDiscountPercent();
        discount = Long.parseLong(sc.nextLine());
        if(discount < 0 || discount > 100) throw new NotCorrectScope();
        return discount;
    }

    private long getFixedDiscount() throws NotCorrectScope {
        long discount;
        consolePrint.printGetDiscountAmount();
        discount = Long.parseLong(sc.nextLine());
        if(discount < 0) throw new NotCorrectScope();
        return discount;
    }
}
