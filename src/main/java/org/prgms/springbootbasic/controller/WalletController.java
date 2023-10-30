package org.prgms.springbootbasic.controller;

import org.prgms.springbootbasic.common.console.WalletConsole;
import org.prgms.springbootbasic.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

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
        UUID customerId = WalletConsole.typeCustomerId();
        UUID voucherId = WalletConsole.typeVoucherId();

        walletService.allocate(customerId, voucherId);
    }

    private void delete(){
        UUID customerId = WalletConsole.typeCustomerId();
        UUID voucherId = WalletConsole.typeVoucherId();

        walletService.delete(customerId, voucherId);
    }

    private void showCustomer(){
        UUID customerId = WalletConsole.typeCustomerId();

        walletService.showVouchersFromCustomer(customerId);
    }

    private void showVoucher(){
        UUID voucherId = WalletConsole.typeVoucherId();

        walletService.showCustomerFromVoucher(voucherId);
    }
}
