package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.controller.CustomerController;
import com.prgrms.vouchermanager.controller.VoucherController;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectScope;
import com.prgrms.vouchermanager.io.Command;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.io.Program;
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
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final Scanner sc = new Scanner(System.in);

    public CommandHandler(ConsolePrint consolePrint, VoucherController voucherController, CustomerController customerController) {
        this.consolePrint = consolePrint;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public Program selectProgram() {
        consolePrint.printProgramSelect();
        String input = sc.nextLine();
        switch (input) {
            case "voucher" -> { return Program.VOUCHER; }
            case "customer" -> { return Program.CUSTOMER; }
            case "exit" -> { return Program.EXIT; }
            default -> throw new NotCorrectCommand(input);
        }
    }

    public Command runVoucherProgram() throws NotCorrectCommand {
        consolePrint.printVoucherFunctionSelect();
        String command = sc.nextLine();
        switch (command) {
            case "create" -> {
                log.info(LogMessage.SELECT_CREATE.getMessage());

                createExecuteVoucher();
                return Command.CREATE;
            }
            case "list" -> {
                log.info(LogMessage.SELECT_LIST.getMessage());

                listExecuteVoucher();
                return Command.LIST;
            }
            case "exit" -> {
                System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
                return Command.EXIT;
            }
            default -> throw new NotCorrectCommand(command);
        }
    }

    public Command runCustomerProgram() throws NotCorrectCommand {
        consolePrint.printCustomerFunctionSelect();
        String command = sc.nextLine();
        switch (command) {
            case "create" -> {
                log.info(LogMessage.SELECT_CREATE.getMessage());

                createExecuteCustomer();
                return Command.CREATE;
            }
            case "list" -> {
                log.info(LogMessage.SELECT_LIST.getMessage());

                listExecuteCustomer();
                return Command.LIST;
            }
            case "exit" -> {
                System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
                return Command.EXIT;
            }
            default -> throw new NotCorrectCommand(command);
        }
    }

    public void createExecuteVoucher() throws NotCorrectForm, NotCorrectScope {
        VoucherType voucherType = getVoucherType();
        long discount = getVoucherDiscount(voucherType);

        log.info(LogMessage.VOUCHER_TYPE_AND_DISCOUNT.getMessage(), voucherType.getType(), discount);

        voucherController.create(voucherType, discount);
        consolePrint.printCompleteCreate();
    }

    public void listExecuteVoucher() throws EmptyListException {
        List<Voucher> vouchers = voucherController.list();
        if(vouchers.isEmpty()) throw new EmptyListException(vouchers);
        else consolePrint.printVoucherList(voucherController.list());
    }

    public void createExecuteCustomer() {
        consolePrint.printGetCustomerName();
        String name = sc.nextLine();
        consolePrint.printGetCustomerYear();
        int year = sc.nextInt();
        sc.nextLine();

        customerController.create(name, year);
        consolePrint.printCompleteCreate();
    }

    public void listExecuteCustomer() throws EmptyListException {
        List<Customer> customers = customerController.list();
        if(customers.isEmpty()) throw new EmptyListException(customers);
        else consolePrint.printCustomerList(customers);
    }
    public void exitExecute() {
        log.info(LogMessage.FINISH_PROGRAM.getMessage());

        System.exit(0);
    }

    public void blackListExecute() throws EmptyListException{
        List<Customer> customers = customerController.blacklist();
        if(customers.isEmpty()) throw new EmptyListException(customers);
        else consolePrint.printBlacklist(customerController.blacklist());
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
                throw new NotCorrectForm(type);
            }
        } catch (NotCorrectForm e) {
            throw new NotCorrectForm(type);
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
            throw new NotCorrectForm(String.valueOf(discount));
        } catch (NotCorrectScope e) {
            throw new NotCorrectScope(discount);
        }

        return discount;
    }

    private long getPercentDiscount() throws NotCorrectScope {
        long discount;
        consolePrint.printGetDiscountPercent();
        discount = Long.parseLong(sc.nextLine());
        if(discount < 0 || discount > 100) throw new NotCorrectScope(discount);
        return discount;
    }

    private long getFixedDiscount() throws NotCorrectScope {
        long discount;
        consolePrint.printGetDiscountAmount();
        discount = Long.parseLong(sc.nextLine());
        if(discount < 0) throw new NotCorrectScope(discount);
        return discount;
    }
}
