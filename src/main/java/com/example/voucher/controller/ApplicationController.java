package com.example.voucher.controller;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.dto.VoucherDTO;
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
            ModeType selectedModeType = console.getSelectedType();

            if (selectedModeType == null) {
                continue;
            }

            switch (selectedModeType) {
                case EXIT -> isRunning = false;
                case CREATE -> createVoucher();
                case LIST -> displayVouchers();
                case DELETE_ALL -> removeVouchers();
            }
        }
    }

    private void createVoucher() {
        VoucherType voucherType = console.getVoucherType();

        if (voucherType == null) {
            return;
        }

        Long discountValue = console.getDiscountValue();

        if (discountValue == null) {
            return;
        }

        try {
            voucherController.createVoucher(voucherType, discountValue);
        } catch (Exception e) {
            console.displayVoucherCreationError();
        }
    }

    private void displayVouchers() {
        List<VoucherDTO> vouchers = voucherController.getVouchers();
        console.displayVoucherInfo(vouchers);
    }

    private void removeVouchers() {
        voucherController.deleteVouchers();
    }

}
