package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.console.Request;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.customer.CustomerController;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.VoucherController;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.wallet.WalletController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController implements CommandLineRunner {

    private final Console console;

    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    private boolean isRunning = true;

    public CommandLineController(Console console, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... arg) {
        console.startProgramMessage();
        while (isRunning) {
            try {
                requestRouting(console.getRequest());
            } catch (RuntimeException e) {
                console.print(e.getMessage());
            }
        }
        console.exitMessage();
    }

    public void requestRouting(Request request) {
        switch (request.getDomain()) {
            case CUSTOMER -> customerController.execute(request.getCommand());
            case WALLET -> walletController.execute(request.getCommand());
            case VOUCHER -> voucherController.execute(request.getCommand());
            case HELP -> console.printHelpMessage();
            case EXIT -> isRunning = false;
        }
    }
}






