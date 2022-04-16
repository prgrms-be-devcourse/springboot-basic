package org.prgms.voucherProgram.domain.program;

import org.prgms.voucherProgram.domain.menu.VoucherMenuType;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;
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
            VoucherMenuType voucherMenuType = inputMenu();
            switch (voucherMenuType) {
                case EXIT -> isNotEndProgram = false;
                case LIST -> printVouchers();
                case CREATE -> createVoucher();
                case BLACKLIST -> printBlackList();
            }
        }
    }

    private void createVoucher() {
        VoucherType voucherType = inputVoucherCommand();
        Voucher voucher = createVoucher(voucherType);
        outputView.printVoucher(voucher);
    }

    private VoucherMenuType inputMenu() {
        while (true) {
            try {
                return VoucherMenuType.from(inputView.inputVoucherMenu());
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
