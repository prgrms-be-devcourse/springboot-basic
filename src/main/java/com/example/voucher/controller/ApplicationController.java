package com.example.voucher.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.constant.VoucherServiceType;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.controller.response.VoucherResponse;
import com.example.voucher.io.Console;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;

    private ApplicationController(VoucherController voucherController) {
        this.console = new Console();
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            ServiceType selectedServiceType = console.getServiceType();

            if (selectedServiceType == null) {
                continue;
            }

            switch (selectedServiceType) {
                case EXIT -> isRunning = false;
                case VOUCHER -> startVoucherService();
            }
        }
    }

    private void startVoucherService() {
        VoucherRequest voucherRequest = console.getVoucherRequest();
        if (voucherRequest == null) {
            return;
        }

        try {
            VoucherResponse voucherResponse = voucherController.run(voucherRequest);

            if (voucherResponse.getVoucherServiceType() == VoucherServiceType.CREATE) {
                console.displayVoucherInfo(voucherResponse.getVoucher());

                return;
            }

            if (voucherResponse.getVoucherServiceType() == VoucherServiceType.LIST) {
                console.displayVoucherInfo(voucherResponse.getVouchers());
            }

        } catch (Exception e) {
            console.displayVoucherCreationError();
        }
    }

}
