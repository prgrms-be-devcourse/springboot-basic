package com.devcourse.springbootbasic.engine;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.io.OutputConsole;
import com.devcourse.springbootbasic.engine.model.Menu;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.VoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Platform {

    @Autowired
    private InputConsole input;

    @Autowired
    private OutputConsole output;

    @Autowired
    private VoucherService voucherService;

    public static void main(String[] args) {
        SpringApplication.run(Platform.class, args)
                .getBean(Platform.class)
                .run();
    }

    public void run() {
        while (true) {
            if (getUserMenuInput()) {
                break;
            }
        }
    }

    private boolean getUserMenuInput() {
        try {
            Menu menu = input.inputMenu();
            return branchByMenu(menu);
        } catch (InvalidDataException e) {
            output.printError(e);
        }
        return false;
    }

    private boolean branchByMenu(Menu menu) {
        return switch (menu) {
            case EXIT -> {
                output.endPlatform();
                yield true;
            }
            case CREATE -> {
                Voucher voucher = createVoucherTask();
                output.printVoucher(voucher);
                yield false;
            }
            case LIST -> {
                listVoucherTask();
                yield false;
            }
        };
    }

    private void listVoucherTask() {
        output.printVouchers(voucherService.getAllVouchers());
    }

    private Voucher createVoucherTask() {
        VoucherType voucherType = voucherTypeInput();
        double voucherDiscount = voucherDiscountInput(voucherType);
        Voucher voucher = voucherType.getVoucherFactory()
                .create(voucherType, voucherDiscount);
        return voucherService.createVoucher(voucher);
    }

    private VoucherType voucherTypeInput() {
        return input.inputVoucherType();
    }

    private double voucherDiscountInput(VoucherType voucherType) {
        return input.inputVoucherDiscount(voucherType);
    }
}
