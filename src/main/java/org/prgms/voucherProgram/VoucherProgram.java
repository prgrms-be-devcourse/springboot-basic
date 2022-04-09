package org.prgms.voucherProgram;

import org.prgms.voucherProgram.entity.MenuType;
import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.prgms.voucherProgram.entity.voucher.VoucherType;
import org.prgms.voucherProgram.exception.WrongDiscountAmountException;
import org.prgms.voucherProgram.exception.WrongDiscountPercentException;
import org.prgms.voucherProgram.service.UserService;
import org.prgms.voucherProgram.service.VoucherService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class VoucherProgram {
    private final VoucherService voucherService;
    private final UserService userService;
    private final InputView inputView;
    private final OutputView outputView;

    public VoucherProgram(VoucherService voucherService, UserService userService, Console console) {
        this.voucherService = voucherService;
        this.userService = userService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            MenuType menuType = inputMenu();
            switch (menuType) {
                case EXIT:
                    isNotEndProgram = false;
                    break;
                case LIST:
                    printVouchers();
                    break;
                case CREATE:
                    VoucherType voucherType = inputVoucherCommand();
                    Voucher voucher = createVoucher(voucherType);
                    outputView.printVoucher(voucher);
                    break;
                case BLACKLIST:
                    printBlackList();
            }
        }
    }

    private void printBlackList() {
        try {
            outputView.printAllUser(userService.findBlackList());
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void printVouchers() {
        try {
            outputView.printAllVoucher(voucherService.findAllVoucher());
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private Voucher createVoucher(VoucherType voucherType) {
        while (true) {
            try {
                long discountValue = inputView.inputDiscountValue(voucherType);
                return voucherService.create(voucherType, discountValue);
            } catch (WrongDiscountPercentException | WrongDiscountAmountException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private VoucherType inputVoucherCommand() {
        while (true) {
            try {
                return VoucherType.findByCommand(inputView.inputVoucherCommand());
            } catch (Exception e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private MenuType inputMenu() {
        while (true) {
            try {
                return MenuType.of(inputView.inputMenu());
            } catch (Exception e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
