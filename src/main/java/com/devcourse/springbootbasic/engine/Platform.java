package com.devcourse.springbootbasic.engine;

import com.devcourse.springbootbasic.engine.config.Message;
import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.io.OutputConsole;
import com.devcourse.springbootbasic.engine.model.ListMenu;
import com.devcourse.springbootbasic.engine.model.Menu;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.domain.VoucherDto;
import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Platform {

    private static final Logger logger = LoggerFactory.getLogger(Platform.class);

    private InputConsole input;
    private OutputConsole output;
    private VoucherService voucherService;

    public Platform(InputConsole input, OutputConsole output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    public void run() {
        logger.debug("Program Engine Created");
        while (true) {
            if (getUserMenuInput()) {
                logger.debug("Program Engine Destroyed");
                break;
            }
        }
    }

    private boolean getUserMenuInput() {
        try {
            Menu menu = input.inputMenu();
            return branchByMenu(menu);
        } catch (InvalidDataException | IOException e) {
            output.printError(e);
            logger.error(e.getMessage(), e.getCause());
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
                new ListTemplate({
                    ListMenu listMenu = input.inputListMenu();
                    data = voucherService.getAllVouchers();
                    output.printVouchers();
                });
                branchListMenu();
                yield false;
            }
            default -> { yield false; }
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
        return voucherService.createVoucher(
                new VoucherDto(
                        voucherType,
                        voucherDiscountInput(voucherType)
                )
        );
    }

    private VoucherType voucherTypeInput() {
        return input.inputVoucherType();
    }

    private double voucherDiscountInput(VoucherType voucherType) {
        return input.inputVoucherDiscount(voucherType);
    }

}
