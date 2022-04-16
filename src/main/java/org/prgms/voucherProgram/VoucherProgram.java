package org.prgms.voucherProgram;

import org.prgms.voucherProgram.entity.MenuType;
import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.prgms.voucherProgram.entity.voucher.VoucherType;
import org.prgms.voucherProgram.exception.WrongFileException;
import org.prgms.voucherProgram.service.CustomerService;
import org.prgms.voucherProgram.service.VoucherService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class VoucherProgram {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final InputView inputView;
    private final OutputView outputView;

    public VoucherProgram(VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            MenuType menuType = inputMenu();
            switch (menuType) {
                case EXIT -> isNotEndProgram = false;
                case LIST -> printVouchers();
                case CREATE -> {
                    VoucherType voucherType = inputVoucherCommand();
                    Voucher voucher = createVoucher(voucherType);
                    outputView.printVoucher(voucher);
                }
                case BLACKLIST -> printBlackList();
            }
        }
    }

    private MenuType inputMenu() {
        while (true) {
            try {
                return MenuType.of(inputView.inputMenu());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printVouchers() {
        try {
            outputView.printVouchers(voucherService.findAllVoucher());
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            System.exit(0);
        }
    }

    private VoucherType inputVoucherCommand() {
        while (true) {
            try {
                return VoucherType.findByCommand(inputView.inputVoucherCommand());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Voucher createVoucher(VoucherType voucherType) {
        while (true) {
            try {
                long discountValue = inputView.inputDiscountValue(voucherType);
                return voucherService.create(voucherType, discountValue);
            } catch (WrongFileException e) {
                outputView.printError(e.getMessage());
                System.exit(0);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printBlackList() {
        try {
            outputView.printCustomers(customerService.findBlackList());
        } catch (WrongFileException e) {
            outputView.printError(e.getMessage());
            System.exit(0);
        }
    }
}
