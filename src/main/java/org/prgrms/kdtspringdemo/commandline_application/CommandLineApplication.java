package org.prgrms.kdtspringdemo.commandline_application;

import org.prgrms.kdtspringdemo.io.console.Console;
import org.prgrms.kdtspringdemo.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private boolean isRunning = true;
    private final Console console;
    private final VoucherService voucherService;
    private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (isRunning) {
            //output 책임
            console.showMenu();
            String myString = console.getInputWithPrompt("");
            try {
                executeMenuByInput(myString);
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
                console.showError(e);
            }
        }
    }

    //분기하는 책임
    private CommandType executeMenuByInput(String myString) throws IllegalArgumentException {

        CommandType commandType = CommandType.getTypeByName(myString);
        switch (commandType) {
            case EXIT -> {
                //종료하는 책임
                logger.info("종료 로직");
                exitProgram();
            }
            case CREATE -> {
                //생성하는 책임
                logger.info("create 로직");
                createVoucher();
            }
            case LIST -> {
                //list를 보여주는 책임
                logger.info("List 로직");
                showVoucherList();
            }
        }
        return commandType;

    }

    private void exitProgram() {
        this.isRunning = false;

    }

    private Voucher createVoucher() {
        try {
            VoucherType voucherType = console.selectVoucherTypeMenu();
            Long value = console.getVoucherValue();
            return voucherService.createVoucher(voucherType, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
            console.showError(e);
            return null;
        }
    }

    private void showVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVoucherList();
        console.showList(voucherList);
    }



}
