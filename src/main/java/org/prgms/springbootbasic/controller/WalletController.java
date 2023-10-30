package org.prgms.springbootbasic.controller;

import org.prgms.springbootbasic.common.console.WalletConsole;
import org.springframework.stereotype.Controller;

@Controller
public class WalletController {
    private final String ALLOCATE = "allocate";
    private final String DELETE = "delete";
    private final String SHOW_CUSTOMER = "showCustomer";
    private final String SHOW_VOUCHER = "showVoucher";

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void run(){
        String command = WalletConsole.readCommand();

        executeCommand(command);
        WalletConsole.success(command);
    }

    private void executeCommand(String command){
        switch (command){
            case ALLOCATE -> allocate();
            case DELETE -> delete();
            case SHOW_CUSTOMER -> showCustomer();
            case SHOW_VOUCHER -> showVoucher();
        }
    }

    private void allocate(){
        String customerId = WalletConsole.typeCustomerId();
        String voucherId = WalletConsole.typeVoucherId();

        walletService.allocate(customerId, voucherId);
    }

    private void delete(){
        String customerId = WalletConsole.typeCustomerId();
        String voucherId = WalletConsole.typeVoucherId();

        walletService.delete(customerId, voucherId);
    }

    private void showCustomer(){
        String customerId = WalletConsole.typeCustomerId();

        walletService.showCustomer(customerId);
    }

    private void showVoucher(){
        String voucherId = WalletConsole.typeVoucherId();

        walletService.showVoucher(voucherId);
    }
}
