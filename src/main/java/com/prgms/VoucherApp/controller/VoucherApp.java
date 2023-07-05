package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.customer.controller.CustomerManagementController;
import com.prgms.VoucherApp.domain.voucher.controller.VoucherManagementController;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.ManagementType;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherApp implements Runnable {

    private final VoucherManagementController voucherManagementController;
    private final CustomerManagementController customerManagementController;
    private final Output output;
    private final Input input;

    public VoucherApp(VoucherManagementController voucherManagementController, CustomerManagementController customerManagementController, Input input, Output output) {
        this.voucherManagementController = voucherManagementController;
        this.customerManagementController = customerManagementController;
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
                voucherManagementController.run();
                continue;
            }

            if (type.isCustomer()) {
                customerManagementController.run();
                continue;
            }

            if (type.isExit()) {
                isPower = false;
            }
        }
    }
}
