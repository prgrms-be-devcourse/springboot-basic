package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.controller.CustomerController;
import com.prgrms.vouchermanager.controller.VoucherController;
import com.prgrms.vouchermanager.exception.*;
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
import java.util.UUID;

@Component
@Slf4j
public class CommandHandler {
    private final ConsolePrint consolePrint;
    private final CustomerExecutor customerExecutor;
    private final VoucherExecutor voucherExecutor;
    private final Scanner sc = new Scanner(System.in);

    public CommandHandler(ConsolePrint consolePrint, CustomerExecutor customerExecutor, VoucherExecutor voucherExecutor) {
        this.consolePrint = consolePrint;
        this.customerExecutor = customerExecutor;
        this.voucherExecutor = voucherExecutor;
    }

    public boolean selectProgram() {
        consolePrint.printProgramSelect();
        String input = sc.nextLine();
        switch (input) {
            case "voucher" -> {
                return runVoucherProgram() != Command.EXIT;
            }
            case "customer" -> {
                return runCustomerProgram() != Command.EXIT;
            }
            case "exit" -> { return false; }
            default -> throw new NotCorrectCommand(input);
        }
    }

    public Command runVoucherProgram() throws NotCorrectCommand {
        consolePrint.printVoucherFunctionSelect();
        String command = sc.nextLine();
        switch (command) {
            case "create" -> {
                log.info(LogMessage.SELECT_CREATE.getMessage());

                voucherExecutor.create();
                return Command.CREATE;
            }
            case "list" -> {
                log.info(LogMessage.SELECT_LIST.getMessage());

                voucherExecutor.list();
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

                customerExecutor.create();
                return Command.CREATE;
            }
            case "list" -> {
                log.info(LogMessage.SELECT_LIST.getMessage());

                customerExecutor.list();
                return Command.LIST;
            }
            case "update" -> {
                customerExecutor.update();
                return Command.UPDATE;
            }
            case "delete" -> {
                customerExecutor.delete();
                return Command.DELETE;
            }
            case "blacklist" -> {
                customerExecutor.blackList();
                return Command.BLACKLIST;
            }
            case "exit" -> {
                System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
                return Command.EXIT;
            }
            default -> throw new NotCorrectCommand(command);
        }
    }
}
