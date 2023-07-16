package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.customer.controller.CustomerManagementController;
import com.prgms.VoucherApp.domain.voucher.controller.VoucherManagementController;
import com.prgms.VoucherApp.domain.wallet.controller.WalletManagementController;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.ManagementType;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherApp implements Runnable {

    private final VoucherManagementController voucherManagementController;
    private final CustomerManagementController customerManagementController;
    private final WalletManagementController walletManagementController;
    private final Output output;
    private final Input input;

    public VoucherApp(VoucherManagementController voucherManagementController,
                      CustomerManagementController customerManagementController,
                      WalletManagementController walletManagementController,
                      Input input,
                      Output output) {
        this.voucherManagementController = voucherManagementController;
        this.customerManagementController = customerManagementController;
        this.walletManagementController = walletManagementController;
        this.input = input;
        this.output = output;
    }


    @Override
    public void run() {
        boolean isPower = true;
        while (isPower) {
            output.printManagementMenu();
            int inputCommand = input.inputManagementCommand();
            ManagementType type = ManagementType.findByType(inputCommand);

            switch (type) {
                case VOUCHER -> {
                    voucherManagementController.run();
                }

                case CUSTOMER -> {
                    customerManagementController.run();
                }

                case WALLET -> {
                    walletManagementController.run();
                }

                case EXIT -> {
                    isPower = false;
                }
            }
        }
    }
}
