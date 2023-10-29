package com.prgrms.vouchermanager.handler;

import com.prgrms.vouchermanager.exception.NotCorrectCommandException;
import com.prgrms.vouchermanager.handler.executor.CustomerExecutor;
import com.prgrms.vouchermanager.handler.executor.VoucherExecutor;
import com.prgrms.vouchermanager.handler.executor.WalletExecutor;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.prgrms.vouchermanager.message.ConsoleMessage.*;

@Component
@Slf4j
public class CommandHandler {
    private final ConsolePrint consolePrint;
    private final CustomerExecutor customerExecutor;
    private final VoucherExecutor voucherExecutor;
    private final WalletExecutor walletExecutor;
    private final Scanner sc = new Scanner(System.in);

    public CommandHandler(ConsolePrint consolePrint, CustomerExecutor customerExecutor, VoucherExecutor voucherExecutor, WalletExecutor walletExecutor) {
        this.consolePrint = consolePrint;
        this.customerExecutor = customerExecutor;
        this.voucherExecutor = voucherExecutor;
        this.walletExecutor = walletExecutor;
    }

    public boolean selectProgram() {
        consolePrint.printMessage(SELECT_PROGRAM.getMessage());
        String input = sc.nextLine();
        switch (input) {
            case "voucher" -> {
                return runVoucherProgram() != Command.EXIT;
            }
            case "customer" -> {
                return runCustomerProgram() != Command.EXIT;
            }
            case "wallet" -> {
                return runWalletProgram() != Command.EXIT;
            }
            case "exit" -> { return false; }
            default -> throw new NotCorrectCommandException(input);
        }
    }

    public Command runVoucherProgram() throws NotCorrectCommandException {
        consolePrint.printMessage(SELECT_FUNCTION_VOUCHER.getMessage());
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
            case "update" -> {
                voucherExecutor.update();
                return Command.UPDATE;
            }
            case "delete" -> {
                voucherExecutor.delete();
                return Command.DELETE;
            }
            case "exit" -> {
                System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
                return Command.EXIT;
            }
            default -> throw new NotCorrectCommandException(command);
        }
    }

    public Command runCustomerProgram() throws NotCorrectCommandException {
        consolePrint.printMessage(SELECT_FUNCTION_CUSTOMER.getMessage());
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
            default -> throw new NotCorrectCommandException(command);
        }
    }

    public Command runWalletProgram() throws NotCorrectCommandException {
        consolePrint.printMessage(SELECT_FUNCTION_WALLET.getMessage());
        String command = sc.nextLine();
        switch (command) {
            case "create" -> {
                walletExecutor.create();
                return Command.CREATE;
            }
            case "voucher" -> {
                log.info(LogMessage.SELECT_LIST.getMessage());

                walletExecutor.findByCustomerId();
                return Command.LIST;
            }
            case "customer" -> {
                walletExecutor.findByVoucherId();
                return Command.UPDATE;
            }
            case "delete" -> {
                walletExecutor.delete();
                return Command.DELETE;
            }
            case "exit" -> {
                System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
                return Command.EXIT;
            }
            default -> throw new NotCorrectCommandException(command);
        }
    }
}
