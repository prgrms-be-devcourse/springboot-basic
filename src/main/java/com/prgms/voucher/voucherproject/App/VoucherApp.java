package com.prgms.voucher.voucherproject.App;

import com.prgms.voucher.voucherproject.domain.voucher.MenuType;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.io.Constant;
import com.prgms.voucher.voucherproject.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherApp {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApp.class);

    private final Console console = new Console();
    private final VoucherService voucherService;

    public VoucherApp(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void voucherRun() throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            MenuType menuType = console.inputVoucherMenu();

            if (menuType == null) continue;

            switch (menuType) {
                case CREATE -> createVoucher();
                case LIST -> list();
                case EXIT -> {
                    isRunning = false;
                    console.printMessage(Constant.PROGRAM_END, true);
                }
            }
        }
    }

    private void list() {
        List<Voucher> voucherList = voucherService.list();

        if (voucherList.isEmpty()) {
            console.printMessage(Constant.NOT_EXITS_VOUCHER, true);
        }

        for (Voucher v : voucherList) {
            console.printVoucherInfo(v);
        }
    }

    private void createVoucher() {
        VoucherType voucherType = console.inputVoucherType();

        if (voucherType == null) return;

        Long discount = console.inputDiscountAmount(voucherType);

        if (discount == null) return;

        try {
            voucherService.create(voucherType, discount);
        } catch (IllegalArgumentException e) {
            logger.error("{}Voucher IllegalArgumentException -> {}", voucherType, discount);
            console.printMessage(e.getLocalizedMessage(), true);
        }
    }

}