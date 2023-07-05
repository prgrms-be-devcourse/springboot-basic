package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.global.io.Console;
import org.prgms.vouchermanagement.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.InputMismatchException;

@Component
public class CommandLineApplication implements CommandLineRunner, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Console console;
    private final VoucherService voucherService;
    private ApplicationContext applicationContext;

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        while (true) {

            console.printCommandMenu();
            try {
                CommandMenu currentCommand = CommandMenu.getCommandMenu(console.getCommand());
                switch (currentCommand) {
                    case EXIT -> { return; }
                    case CREATE_NEW_VOUCHER -> voucherService.createNewVoucher();
                    case SHOW_VOUCHER_LIST -> voucherService.showVoucherList();
                    case SHOW_BLACK_LIST -> showBlackList();
                    default -> throw new IllegalArgumentException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);
                }
            } catch (NoSuchFileException e) {
                logger.error("No csv file Error");
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                logger.error("Command Input Error");
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                logger.info("RuntimeException Error");
                System.out.println(e.getMessage());
            }
        }
    }

    private void showBlackList() throws NoSuchFileException {
        Resource resource = applicationContext.getResource("customer_blacklist.csv");
        try {
            console.printCustomerBlackList(resource.getFile().toPath().toString());
        } catch (IOException e){
            throw new NoSuchFileException(ExceptionMessageConstant.NO_BLACK_LIST_FILE_EXCEPTION);
        }
    }
}
