package com.example.voucher;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.controller.VoucherController;
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
        boolean isOn = true;

        while (isOn) {
            ModeType selectedModeType = console.getSelectedType();

            if (selectedModeType == null) {
                continue;
            }

            switch (selectedModeType) {
                case EXIT -> isOn = false;
                case CREATE -> processCreate();
                case LIST -> processList();
            }
        }
    }

    private void processCreate() {
        VoucherType voucherType = console.readVoucherType();
        long discountValue = console.readDiscountValue();

        voucherController.createVoucher(voucherType, discountValue);
    }

    private void processList() {
        List<VoucherDTO> vouchers = voucherController.getVouchers();
        console.printVoucherInfo(vouchers);
    }

}
