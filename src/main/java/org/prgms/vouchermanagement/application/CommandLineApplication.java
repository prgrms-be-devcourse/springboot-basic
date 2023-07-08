package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.customer.service.CustomerService;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.global.io.Console;
import org.prgms.vouchermanagement.voucher.exception.VoucherException;
import org.prgms.vouchermanagement.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.NoSuchFileException;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Console console;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(Console console, VoucherService voucherService, CustomerService customerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run(String... args) {
        while (true) {
            console.printCommandMenu();
            try {
                CommandMenu currentCommand = CommandMenu.getCommandMenu(console.getCommand());
                switch (currentCommand) {
                    case EXIT -> { return; }
                    case CREATE_NEW_VOUCHER -> voucherService.createNewVoucher();
                    case SHOW_VOUCHER_LIST -> voucherService.showVoucherList();
                    case SHOW_BLACK_LIST -> customerService.showBlackList();
                    case SHOW_CUSTOMER_LIST -> customerService.showCustomerList();
                    default -> throw new IllegalArgumentException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);
                }
            } catch (NoSuchFileException e) {
                logger.error("No csv file Error", e);
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.error("Command Input Error", e);
                System.out.println(e.getMessage());
            } catch (VoucherException e) {
                logger.error("Voucher Input Error", e);
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                logger.error("RuntimeException Error", e);
                System.out.println(e.getMessage());
            }
        }
    }
}
