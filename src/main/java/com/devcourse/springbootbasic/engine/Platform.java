package com.devcourse.springbootbasic.engine;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.io.OutputConsole;
import com.devcourse.springbootbasic.engine.model.ListMenu;
import com.devcourse.springbootbasic.engine.model.Menu;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Platform {

    @Autowired
    private InputConsole input;

    @Autowired
    private OutputConsole output;

    @Autowired
    private VoucherService voucherService;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean branchByMenu(Menu menu) throws IOException {
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
                branchListMenu();
                yield false;
            }
        };
    }

    private void branchListMenu() throws IOException {
        ListMenu listMenu = input.inputListMenu();
        switch (listMenu) {
            case VOUCHER_LIST -> {
                listVoucherTask();
            }
            case BLACK_CUSTOMER_LIST -> {
                listBlackCustomerTask();
            }
        }
    }

    private void listBlackCustomerTask() throws IOException {
        output.printBlackCustomers(input.getBlackCustomers());
    }

    private void listVoucherTask() {
        output.printVouchers(voucherService.getAllVouchers());
    }

    private Voucher createVoucherTask() {
        VoucherType voucherType = voucherTypeInput();
        double voucherDiscount = voucherDiscountInput(voucherType);
        Voucher voucher = voucherType.getVoucherFactory()
                .create(voucherDiscount);
        return voucherService.createVoucher(voucher);
    }

    private VoucherType voucherTypeInput() {
        return input.inputVoucherType();
    }

    private double voucherDiscountInput(VoucherType voucherType) {
        return input.inputVoucherDiscount(voucherType);
    }
}
