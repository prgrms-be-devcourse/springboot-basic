package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.customer.controller.CustomerManagementApp;
import com.prgms.VoucherApp.domain.voucher.controller.VoucherManagementApp;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.ManagementType;
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
            output.printManagementMenu();
            int inputCommand = input.inputManagementCommand();
            ManagementType type = ManagementType.findByType(inputCommand);

            if (type.isVoucher()) {
                voucherManagementApp.run();
                continue;
            }

            if (type.isCustomer()) {
                customerManagementApp.run();
                continue;
            }

            if (type.isExit()) {
                isPower = false;
            }
        }
    }
}
