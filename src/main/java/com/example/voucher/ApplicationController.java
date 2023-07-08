package com.example.voucher;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.controller.VoucherController;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.io.Console;

@Controller
public class ApplicationController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

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

            if (selectedModeType == ModeType.NONE) {
                continue;
            }

            switch (selectedModeType) {
                case EXIT -> isRunning = false;
                case CREATE -> createVoucher();
                case LIST -> displayVouchers();
            }
        }
    }

    private void createVoucher() {
        boolean isCreated = true;

        try {
            VoucherType voucherType = console.getVoucherType();
            long discountValue = console.getDiscountValue();
            voucherController.createVoucher(voucherType, discountValue);

        } catch (Exception e) {
            logger.error(e.getMessage());
            isCreated = false;

        } finally {
            console.displayCreateResult(isCreated);
            
        }
    }

    private void displayVouchers() {
        List<VoucherDTO> vouchers = voucherController.getVouchers();
        console.displayVoucherInfo(vouchers);
    }

}
