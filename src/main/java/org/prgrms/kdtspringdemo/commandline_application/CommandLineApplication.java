package org.prgrms.kdtspringdemo.commandline_application;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.CustomerService;
import org.prgrms.kdtspringdemo.io.console.Console;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.exception.FailCreateVoucherException;
import org.prgrms.kdtspringdemo.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private final Console console;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Console console, VoucherService voucherService, CustomerService customerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(ApplicationArguments args) {
        CommandType selectedCommand = null;
        while (selectedCommand != CommandType.EXIT) {
            console.showMenu();
            String userInput = console.getInputWithPrompt("type :");
            selectedCommand = selectMenuByInput(userInput);
        }
    }

    private CommandType selectMenuByInput(String menu) {

        try {
            CommandType commandType = CommandType.getTypeByName(menu);
            switch (commandType) {
                case EXIT -> {
                    logger.info("종료 로직");
                }
                case CREATE -> {
                    logger.info("create 로직");
                    createVoucher();
                }
                case LIST -> {
                    logger.info("List 로직");
                    showVoucherList();
                }
                case BLACK -> {
                    logger.info("블랙리스트 로직");
                    showBlackList();
                }
            }
            return commandType;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            console.showError(e);
            return CommandType.ERROR;
        } catch (FailCreateVoucherException e) {
            logger.error(e.getMessage());
            console.showError(e);
            return CommandType.CREATE;
        }

    }

    private void showBlackList() {
        List<Customer> blackList = customerService.getAllBlackList();
        console.showBlackCustomerList(blackList);
    }

    private Voucher createVoucher() {
        try {
            VoucherType voucherType = console.selectVoucherTypeMenu();
            Long value = console.getVoucherValue();
            return voucherService.createVoucher(voucherType, value);
        } catch (Exception e) {
            throw new FailCreateVoucherException(e.getMessage());
        }
    }

    private void showVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVoucherList();
        console.showVoucherList(voucherList);
    }


}
