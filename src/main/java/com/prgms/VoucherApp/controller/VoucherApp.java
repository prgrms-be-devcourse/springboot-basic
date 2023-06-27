package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.customer.controller.CustomerManagementApp;
import com.prgms.VoucherApp.domain.voucher.controller.VoucherManagementApp;
import com.prgms.VoucherApp.view.Command;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherApp implements Runnable {

    private final VoucherManagementApp voucherManagementApp;
    private final CustomerManagementApp customerManagementApp;
    private final Output output;
    private final Input input;

    public VoucherApp(VoucherManagementApp voucherManagementApp, CustomerManagementApp customerManagementApp, Input input, Output output) {
        this.voucherManagementApp = voucherManagementApp;
        this.customerManagementApp = customerManagementApp;
        this.input = input;
        this.output = output;
    }


    @Override
    public void run() {
        boolean isPower = true;
        while (isPower) {
            output.printDisplayMenu();
            String inputCommand = input.inputCommand();
            Command command = Command.findByCommand(inputCommand);
            switch (command) {
                case EXIT -> {
                    isPower = false;
                    return;
                }

                case CREATE -> {
                    voucherManagementApp.createVoucher();
                }

                case LIST -> {
                    voucherManagementApp.readVouchers();
                }

                case BLACKLIST -> {
                }
            }
        }
    }
}
